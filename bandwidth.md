# Bandwidth

Os momentos de uso da rede ocorre somente quando o app necessita acessar o firebase, que é o serviço
que estamos utilizando como o servidor. Exemplos de uso de rede:
- Registro do usuário: salvamos suas informações
- Login: é feito o casamento de id e senha
- Criação/Registro de disciplina: é salvo as informações (hora, dia) ou relação aluno/professor com
a disciplina
- Localização: é salvo as localizações dos usuários para ser feito o casamento entre aluno e professor
para marcar a presença
- Listagem das informações de aulas

O primeiro pico da imagem abaixo é no momento que ele faz a requisição para o Location Manager para
pegar localização, já o segundo uma requisição para o Firebase.

![Image](/attachments/alarm_network.png)

## Boas práticas
O alarme de início/fim e matcher de aula são sempre desabilitados após concluirem sua função. 
Isso evita que alarmes fiquem acordando e consumindo banda para acessar o Firebase.