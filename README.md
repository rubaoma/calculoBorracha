# calculoBorracha

Aplicativo de calculo de peso de borracha.

Meu primeiro app do zero, o mesmo serve para resolver o problema da empresa que utiliza,
esse calculo tanto para produção de borracha tanto para cobrança do servico para os seus clientes.
A principio o calculo é realizado em uma planilha eletronica (excel).
Então nasceu a necessidade de portabilidade de calcular tanto dentro da producao de borracha, tanto para o time de vendas da empresa.

O Aplicativo é formado por uma tela principal onde há as opções:

Calcular Peso
Calcular Valor

Essas opções leva cada uma a uma activity diferente.

ACTIVITY CALCULAR PESO.
A activity Peso possui a entrada das medidas do cilindro que será revestido de borracha, medidas essa que tem que estar em milimetros mm.
Há a seleção do tipo de borracha pois cada uma tem um peso teórico diferente impactando no valor final do calculo.
Há a opção de "capa" onde é calculado o peso sem o sobreexcesso do calculo normal, isso é necessário para uma fase do revestimento.
e tem a opção de calcular e voltar a tela inicial
Backlog: implementar um botão para limpar os campos.

ACTIVITY CALCULAR VALOR:
Backlog: a implementar
A Activity calcular Valor possui as mesmas entradas da activity Calcular peso, menos a opção "Capa" que será substituida por um campo para inserir o valor cobrado por 1 quilo da borracha.
