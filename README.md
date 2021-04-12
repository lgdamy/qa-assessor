# Gerador de CPF
### Projeto simples que gera o dígito verificador de um cpf digitado, ele está em swing

A lógica é bastante simples:
Soma dos 9 primeiros digitos com peso proporcional a posição, e mod11 para o primeiro dígito, e o mesmo cálculo é feito depois que o primeiro dígito foi calculado.

* Releases (Necessário no minimo uma jre8)
  * v1.0.1: [Gerador_de_CPF.jar](https://github.com/lgdamy/gerador-cpf/releases/download/v1.0.1/Gerador_de_CPF.jar)
  * v1.0.0: [Gerador_de_CPF.jar](https://github.com/lgdamy/gerador-cpf/releases/download/v1.0.0/Gerador_de_CPF.jar) 

* Possíveis melhorias:
  * Relacionar o nono dígito a uma UF emissora
  * Consulta do cpf na Receita Federal para validar a existência mesmo

Sinta-se livre para modificar, a casa é sua :wink:
