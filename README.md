# 🕹️ Jogo de Damas

## 🎓 UNIVERSIDADE DO ESTADO DA BAHIA  
**Autores:** Leonardo de Jesus e Luigi Mateus  
**Data:** 24/04/2025  
**Campus:** Alagoinhas  

---

## 📂 Estrutura do Projeto

Lady_game/
├── src/
│ ├── abstraction/ # Camada de abstração (interfaces)
│ │ ├── BoardViewer.java
│ │ ├── CheckersController.java
│ │ └── CheckersEvent.java
│ ├── assets/ # Recursos visuais (imagens)
│ │ ├── leo.png
│ │ ├── luigi.png
│ │ ├── pedraBranca.png
│ │ └── pedraVermelha.png
│ ├── implementation/
│ │ ├── core/
│ │ │ ├── CheckerLogic.java
│ │ │ └── CheckerLogicImp.java
│ │ └── model/ # Lógica do jogo e entidades
│ │ ├── Board.java
│ │ ├── Movement.java
│ │ ├── Peace.java
│ │ ├── Player.java
│ │ └── PositionCheckers.java
│ ├── service/ # Serviços auxiliares (se houver)
│ └── ui/ # Interface do usuário
│ ├── BoardViewSwing.java
│ └── Main.java
├── .gitignore
├── lady_game.iml


---

## ✅ Requisitos Funcionais

- Menu inicial com opções:
  - Escolher movimento de abertura
  - Iniciar o jogo
  - Sair do jogo
- Movimentação das peças no tabuleiro
- Captura de peças inimigas
- Promoção de peça a dama ao alcançar a oitava linha
- Exibição do placar com número de capturas
- Timer para controle do tempo da partida
- Botão de rendição para desistência
- Botão para reinício da partida

---

## 📜 Regras de Negócio

### 🧩 Movimentação
- **Peças comuns:** movem-se apenas para frente, uma casa por vez.
- **Damas:** movem-se para frente e para trás, quantas casas quiserem (sem pular peças da mesma cor).

### 🔪 Captura
- Peças comuns e damas podem capturar para frente e para trás.
- Peças e damas têm o mesmo valor (uma pode capturar a outra).
- É obrigatório capturar a maior quantidade de peças possíveis em um único lance.
- Capturas múltiplas são permitidas, desde que não haja uma peça da mesma cor atrás das adversárias que bloqueie o movimento.

### 🏆 Promoção
- Uma peça que alcança a oitava linha é promovida a dama.

---

## 🎯 Design Patterns Utilizados

### Bridge
- **Objetivo:** Separar a interface de usuário da lógica do jogo.
- **Benefício:** Permite evolução independente entre a interface e as regras do jogo (por exemplo, mudança no número de casas do tabuleiro).

### Template Method
- **Objetivo:** Definir o fluxo fixo do jogo com possibilidade de personalização de certos passos.
- **Aplicação:** Utilização de classes abstratas com métodos que podem ser sobrescritos pelas subclasses para alterar comportamentos específicos.

---

## 🚀 Como Rodar o Projeto

1. Clone o repositório:
   ```bash
   git clone <URL-do-repositório>
Abra com o IntelliJ IDEA ou sua IDE favorita.

Execute a classe Main (localizada em src/ui/Main.java
