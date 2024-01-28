# language: pt
Funcionalidade: API - Pagamentos

  @smoke
  Cenário: Criar um pagamento
    Quando submeter um novo pagamento
    Então o pagamento é gerado e registrado com sucesso

  Cenário: Consultar um pagamento
    Dado que um pagamento já foi registrado
    Quando requisitar a busca de um pagamento
    Então o status do pagamento é exibido com sucesso

  Cenário: Efetuar o pagamento
    Dado que um pagamento já foi registrado
    Quando requisitar a confirmação do pagamento
    Então o pagamento é confirmado e atualizado
