# 🧙‍♂️ RPG por Estruturas de Dados (Java)

## 📘 Descrição
Sistema completo de **batalhas em RPG baseado em turnos**, implementado **100% com estruturas encadeadas** — **sem usar `ArrayList`**.  
O projeto demonstra o uso prático de **listas, pilhas e filas** em Java, integrando cadastros, batalhas PVP e PVE, progressão de nível e economia de moedas.

---

## ⚙️ Estruturas de Dados Utilizadas

| Estrutura | Implementação | Função |
|------------|----------------|--------|
| **Lista Encadeada** | `ListaJogador`, `ListaPersonagem`, `ListaMonstro` | Armazena jogadores, personagens e monstros. |
| **Fila Encadeada** | `FilaArena` | Controla a **ordem dos turnos** durante o combate. |
| **Pilha Encadeada** | `PilhaArena` | Armazena o **ranking e histórico de mortes** dos personagens. |

---

## 🧩 Classes Principais

### 🧑‍💼 `Jogador`
- Pode cadastrar, selecionar e curar personagens.
- Possui saldo de moedas.
- Pode lutar em modo **PvP** (contra outro jogador) ou **PvE** (contra monstros).

### ⚔️ `Personagem`
- Contém atributos de vida, ataque e experiência.
- Pode atacar, curar-se e subir de nível automaticamente.
- Cada nível aumenta **vida máxima** e **dano base**.
- Começa com **5 curas por batalha**.

### 👹 `Monstro`
- Inimigo controlado pelo sistema.
- Possui recompensas de XP e moedas.
- Ataca de forma automática e aleatória.

### 🏟️ `Arena`
- Controla os combates **PvP** e **PvE**.
- Usa uma **Fila** para definir a ordem dos turnos.
- Usa uma **Pilha** para gerar o **ranking final**.
- Remove automaticamente personagens derrotados do jogador.

### 💾 `Menu` e `App`
- Menu de navegação principal e de jogador.
- Permite login, criação de personagens, combates e curas.

---

## 🧠 Funcionalidades

✅ Cadastro e login de jogadores  
✅ Criação e listagem de personagens  
✅ Seleção de personagem ativo  
✅ Combate **PvP** (Jogador vs Jogador)  
✅ Combate **PvE** (Jogador vs Monstros)  
✅ Cura fora de combate (paga em moedas)  
✅ Recarregamento de curas (65 moedas)  
✅ Subida automática de nível (ganha ataque e vida máxima)  
✅ Exclusão automática de personagem morto  
✅ Ranking por dano causado  
✅ Interface 100% terminal  

---

## 💰 Economia do Jogo
- Cada vitória concede moedas ao jogador.
- Moedas podem ser usadas para:
  - **Curar personagem fora de combate**
  - **Recarregar cargas de cura**
- Custo da cura = `0.5 moedas por ponto de HP`
- Recarregar cura = `65 moedas por carga`

---

## 📈 Progressão de Nível
| Ação | XP Ganha |
|-------|-----------|
| Derrotar Goblin | +30 XP |
| Derrotar Orc | +60 XP |

**A cada 100 XP:**
- +1 Nível  
- +1 Ataque  
- +5 Vida Máxima  
- Vida totalmente restaurada  

---

## 🕹️ Execução

1. Compile tudo:
   ```bash
   javac *.java
   ```

2. Execute:
   ```bash
   java App
   ```

3. Use o menu principal:
   ```
   1 - Cadastrar Jogador
   2 - Login
   3 - Sair
   ```

4. Dentro do menu do jogador:
   ```
   1 - Listar Personagens
   2 - Criar Personagem
   3 - Selecionar Personagem
   4 - Iniciar Combate PvP
   5 - Iniciar Combate PvE
   6 - Curar Personagem (fora do combate)
   7 - Recarregar Cura (65 moedas)
   8 - Sair
   ```

---

## 🧾 Créditos Técnicos
- Linguagem: **Java 17+**
- Paradigma: **POO + Estruturas de Dados**
- Desenvolvido por: **Victor Sampaio, Ryan Cavalcanti, Icaro Santos e Pedro Augusto**
- Revisado por: **Lume (ChatGPT GPT-5)**  
