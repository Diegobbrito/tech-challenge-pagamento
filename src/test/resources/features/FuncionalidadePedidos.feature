# language: pt
Funcionalidade: API - Pedidos

  @smoke
  Cenário: Listar todas as categorias
    Quando requisitar a lista de categorias
    Então a lista de categorias são exibidas com sucesso

  Cenário: Registrar um novo produto
    Quando submeter um novo produto
    Então o produto é registrado com sucesso

  Cenário: Listar todos os produtos
    Dado que um produto já foi registrado
    Quando requisitar a busca de produtos
    Então a lista de todos produtos é exibida com sucesso

  Cenário: Listar produtos por Ids
    Dado que um produto já foi registrado
    Quando requisitar a busca de produtos por ids
    Então a lista dos produtos é exibida com sucesso

  Cenário: Buscar produtos de uma categoria
    Dado que um produto já foi registrado
    Quando requisitar a busca de produtos de uma categoria
    Então a lista de produtos da categoria é exibida com sucesso

  Cenário: Alterar um produto existente
    Dado que um produto já foi registrado
    Quando requisitar a alteração do produto
    Então o produto é atualizado com sucesso

  Cenário: Excluir um produto existente
    Dado que um produto já foi registrado
    Quando requisitar a exclusão do produto
    Então o produto é removido com sucesso