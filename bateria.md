# Bateria

## Estudante e Professor

Dado a divisão entre CPU, rede e localização, este fluxo envolve, basicamente, o consumo de energia
da própria CPU, logo pode-se notar que em momentos de pico da CPU também a picos na energia que varia
entre `Medium` e `Light`. Há também alguns momentos que o consumo de energia para rede passa a existir,
esses momentos coincidem com as requisições ao firebase.

## Alarmes

Quando o sistema chama os callbacks, o consumo de CPU se mantém em Light, já quando o callback é do
Location Manager pode-se notar que Location e Network vão para Light (imagem 1), o motivo de Network
também mudar é que o Location Manager pede localização por meio do NETWORK_PROVIDER,utilizando o WIFI
e provendo um resultado mais acurado. Quando o alarme de final de classe dispara, Network vai para
Light devido a requisição feita ao Firebase para salvar as localizações coletadas durante o periodo
da aula.

![Image 1](/attachments/alarm_bateria_location.png)