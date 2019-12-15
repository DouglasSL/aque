# Memória

## Estudante, Professor e Alarmes

Nota-se que a inicialização tem um custo de memória alto devido ao que é definido no profiler como seção native,
que se refere a objetos usados no processamento de ativos de imagem e outros gráficos. Após este passo
o consumo de memória se mantém estável e os gráficos conrinuam a ocupar a maior parte da memória alocada (imagem 1).
Não foram notadas nenhuma diferença significante no consumo de memória durante o uso dos alarmes.

![Image](/attachments/memory.png)

## Memory leak

Para realizar o teste de memory leak (vazamento de mémoria), foi utilizada a ferramenta LeakCanary. O LeakCanary 
permite que objetos como as activities do app sejam assistidas para ver se ocorreu algum tipo de vazamento de memória.
Com isso foi adicionado um _watcher_ para analisar que se ocorreria algum tipo de vazamento de memória entre as activities.
O LeakCanary por padrão, possui uma configuração de um _treshold_ de memória retida pelo app, que é 5, a condição de 
vazamento de memória foi baseada nesse limite. Nosso app inicialmente apresentou problemas de vazamento de memória, onde 
o treshold era rapidamente atingido.

### Melhorias

Primeiramente como melhoria, foi removido de todas as activities o histórico do fluxo de telas para que não ficasse guardado
todas as telas na memória (apagar o histórico não afetaria a funcionalidade do app). Ainda assim, com interação entre telas que 
continham adapter, pode-se perceber que vazamentos de mémória ocorriam a ponto de passar do limite definido pelo LeakCanary. Foi observado que o essas tipos de tela, seus adapters guardavam uma refrência para outra activity. Como melhoria, o método _onDestroy()_ dessas activities foi reimplementado, para que ele agora remova as referências guardadas no adapter. Após estas melhorias, mesmo seguindo fluxos aleatórios (feitos manualmente), não pode-se atingir o treshold definido pela ferramenta.