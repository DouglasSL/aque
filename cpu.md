# CPU

## Estudante e Professor

Segue o fluxo utilizado nesta etapa:
- Criação de conta
- Login
- Criação/Registro de disciplina

Foi notado que o app em si não consume muita CPU. Notamos que em média o app utiliza 6% da capacidade
da CPU do aparelho, enquanto que o sistema e aplicativos em background no celular consumiam 10% acima do app.
Percebe-se também que o número de threads aumentou significativamente. Isso é explicável devido a necessidade
de acesso ao banco de dados e firebase.

Os picos que foram notados no consumo da CPU durante a execução do teste foram relacionados a criação das
activities, acesso ao firebase (imagem 1), inicialização do shared preferences e alarm manager (imagem 2):


![Image1](/attachments/cpu_1.png)
##### Imagem 1 - registro do usuário (inicializa o Firebase e salva dados)

![Image2](/attachments/cpu_2.png)
##### Imagem 1 - após login do usuário (inicializa shared preferences, checa se a conta é valida e seta alarme)

## Alarmes

O consumo de CPU para os alarmes é cerca de 1% a mais do consumo anterior quando eles despertam para
executar o código dos receivers (imagem 3) e para receber a localização pelo callback do Location Manager
(imagem 4). Quando o app não está em uso, é facilmente detectável os picos de CPU quando o sistema chama
os callbacks.

![Image3](/attachments/alarm_receiver_cpu.png)
##### Imagem 3 - receiver

![Image4](/attachments/alarm_request_location_cpu.png)
##### Imagem 4 - request location