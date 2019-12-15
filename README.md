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

#### Fluxo de Telas

O fluxo de telas entre aluno e progessor são muito semelhantes. Inicialmente existe a tela inicial
onde o usuário possui a opção de querer logar como aluno ou professor. Após isso uma tela de login
é mostrada ao usuário que se já cadastrado pode logar, senão, pode se cadastrar. Após o usuário
ser cadastrado, ele pode logar e será redirecionada para as telas principais do aluno ou professor.
Em ambas as telas pode ser visualizado informaações de disciplinas que no caso do professor, ele criou
e no caso do aluno ele se matriculou. Ainda na tela principal, existe um botão com um **+**, que
para o aluno leva a uma tela de se matricular na disciplina, e para o professor leva a criação de uma
disciplina. Para o professor em sua tela principal, ele pode clicar em uma de suas disciplinas criadas
e será redirecionado para uma tela que lista todas as datas que já houve aula para aquela disciplina
e a quantidade de alunos presentes naquela data. Ao clicar em um desses itens, ele é redirecionado
para uma tela, que lista o email de todos os alunos presentes naquele dia naquela disciplina
específica. E assim conclui todo o fluxo de telas.

### Desenvolvimento

O projeto pode ser dividido entre:

* Servidor - Foi utilizado o Firebase
* Aplicação:
   - Aluno
   - Professor

Tendo em vista essas especificações, ainda não temos ideia da carga de trabalho, o que no futuro será analisado para ser dividido igualmente entre os membros da dupla.
O servidor será responsável por receber as requisições para validar as presenças com o algoritmo implementado

### Testes

Nossos testes foram divididos em duas partes, um fluxo para o aluno e o professor (que são basicamente
fluxo de telas), e outro fluxo para testes dos alrmes que não necessariamente estaria com o app aberto.

#### Ambiente de Testes

@Douglas aqui PLEASEEEEEE!!!!

#### Estudante e Professor

Para o fluxo de estudante e professor, o fluxo de telas que foi seguido é descrito como:
1 - Registra o estudante ou professor;
2 - Login;
3 - professor cria nova disciplina, estudante se matricula em uma nova disciplina;

- [Douglas Soares](https://github.com/DouglasSL)
- [Jônatas Clementino](https://github.com/JonatasDeOliveira)
