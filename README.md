# Aquê!

Aplicativo Android de chamadas automáticas.

### Proposta do Aplicativo

O tempo de aula, muitas vezes, é curto para a discussão e apresentação de conteúdos curriculares em todos os níveis de ensino. Em casos onde as turmas são grandes os professores perdem parte deste tempo com chamadas. 
O *Aquê!* tem como objetivo remover a necessidade do professor perder seu precioso tempo, que poderia estar educando os alunos, para fazer chamada. 
Com isso, o professor só precisa cadastrar uma sala com o horário de suas aulas e o aplicativo dos alunos irão acordar durante este período para fazer a validação da presença.

### Público alvo

Professores para criação das classes e alunos para confirmar presenças nas aulas.

### Concorrentes

Não encontramos nenhum aplicativo no mercado que possua o mesmo objetivo proposto acima, os aplicativos de chamadas que foram encontrados são meramente cadernetas de chamadas eletrônicas.

### Funcionamento

O funcionamento de presença é baseado por meio de alarmes:

#### Routine Alarm
Dispara todos os dias às 6 da manhã e é responsável por ligar os alarmes da primeira aula (início e final)

#### Class Alarm
Utiliza de extras do intent para se guiar entre início da aula e final da aula. Caso seu alarme tocar e for início de aula
é responsável por ativar o alarme de localização. Já o de final de aula é responsável por pegar todas as localizações coletadas
durante a aula e enviar ao Firebase, caso o usuário seja um professor, ele também ativa o alarme de matcher. Além disso,
é também responsável por checar por próximas aulas que o usuário venha a ter.

#### Location Alarm
Fica disparando de 15 em 15 minutos para coletar a localização do usuário e salva no banco de dados local.

#### Matcher Alarm
Responsável por fazer o casamento de localizações entre professores e alunos.

### Desenvolvimento

O projeto pode ser dividido entre:

* Servidor - Foi utilizado o Firebase
* Aplicação:
   - Aluno
   - Professor

Tendo em vista essas especificações, ainda não temos ideia da carga de trabalho, o que no futuro será analisado para ser dividido igualmente entre os membros da dupla.
O servidor será responsável por receber as requisições para validar as presenças com o algoritmo implementado

- [Douglas Soares](https://github.com/DouglasSL)
- [Jônatas Clementino](https://github.com/JonatasDeOliveira)
