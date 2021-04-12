package com.damytec.qaassessor.service;

import com.damytec.qaassessor.pojo.CreditCard;
import com.damytec.qaassessor.util.StringUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lgdamy@raiadrogasil.com on 22/01/2021
 */
public class CreditCardAssessorService {

    private static final char ZERO = '0';
    private static CreditCardAssessorService INSTANCE;

    private CreditCardAssessorService() {}

    public static CreditCardAssessorService getInstance() {
        return INSTANCE = INSTANCE == null ? new CreditCardAssessorService() : INSTANCE;
    }

    public CreditCard calcularDV(String cc) throws Exception {
        if (!StringUtil.isNumeric(cc)) {
            throw new Exception("Um cart\u00e3o deve conter apenas n\u00fameros");
        }
        Bandeira bandeira = Bandeira.getBandeira(cc).orElse(Bandeira.GENERICA);
        if (cc.length() > bandeira.digits.stream().max(Integer::compareTo).get()) {
            throw new Exception(String.format("%s n\u00e3o pode ter mais que %d d\u00edgitos", bandeira.toString(), bandeira.digits.stream().max(Integer::compareTo).get()));
        }
        if (bandeira.digits.stream().anyMatch(d -> d == cc.length()) && isValido(cc)) {
            return toPojo(bandeira,cc);
        }
        int mindig = bandeira.digits.stream().filter(d -> d > cc.length()).min(Integer::compareTo)
                .orElseThrow(() ->
                new Exception("D\u00edgito verificador inv\u00e1lido")) -1;
        String filled = StringUtil.rightPad(cc, mindig, ZERO);
        for (int i = 0; i <= 9; i++ ) {
            String tocheck = String.format("%s%d", filled,i);
            if (isValido(tocheck)) {
                return toPojo(bandeira, tocheck);
            }
        }
        throw new Exception("Cart\u00e3o imposs\u00edvel de ser gerado");
    }

    private boolean isValido(String cc)
    {
        int nDigits = cc.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = cc.charAt(i) - ZERO;

            if (isSecond == true)
                d = d * 2;

            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }

    private CreditCard toPojo(Bandeira b, String cc) {
        CreditCard pojo = new CreditCard();
        pojo.setDv(cc.substring(cc.length() -1 ));
        pojo.setFormatted(cc.substring(0,4) + " " + cc.substring(4,8) + " " + cc.substring(8,12) + " " + cc.substring(12));
        pojo.setRaw(cc);
        pojo.setFlag(b.toString());
        return pojo;
    }


    private enum Bandeira {
        VISA(new Integer[]{4},new Integer[]{13,16}),
        MASTER_CARD(new Integer[]{5}, new Integer[]{16}),
        DINERS(new Integer[]{301,305,36,38}, new Integer[]{14,16}),
        ELO(new Integer[]{636368,438935,504175,451416,509048,509067,509049,509069,509050,509074,509068,509040, 509045,509051,509046,509066,509047,509042,509052,509043,509064,509040,36297, 5067,4576,4011}, new Integer[]{16}),
        AMERICAN_EXPRESS(new Integer[]{34,37}, new Integer[]{15}),
        DISCOVER(new Integer[]{6011,622,64,65}, new Integer[]{16}),
        AURA(new Integer[]{50}, new Integer[]{16}),
        JCB(new Integer[]{35}, new Integer[]{16}),
        HIPERCARD(new Integer[]{38,60}, new Integer[]{13,16,19}),
        GENERICA(new Integer[]{}, new Integer[]{13,14,15,16,17,18,19});

        Bandeira(Integer[] identifiers, Integer[] digits) {
            this.digits = Arrays.asList(digits);
            this.identifiers = Arrays.stream(identifiers).map(String::valueOf).collect(Collectors.toList());
        }

        private List<Integer> digits;
        private List<String> identifiers;

        private static Optional<Bandeira> getBandeira(String cc) {
            return EnumSet.allOf(Bandeira.class).stream()
                    .filter(b -> b.identifiers.stream().anyMatch(cc::startsWith))
                    .max(Comparator.comparing(b -> b.identifiers.stream().filter(cc::startsWith).findFirst().get().length()));
        }

        @Override
        public String toString() {
            return StringUtil.capitalize(name().replace("_"," "));
        }
    }
}
