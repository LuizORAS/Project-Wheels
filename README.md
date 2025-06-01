# Project Wheels - Assinaturas Diversas 🚲

**Project Wheels - Assinaturas Diversas** é uma aplicação Java para gerenciamento de usuários e planos de aluguel de bicicletas, focada em uma experiência simples e prática, tanto para programadores quanto para usuários finais.

## Visão Geral

Este projeto simula o backend de um sistema de aluguel de bicicletas, com gestão de diferentes planos (FREE, BASIC, GOLD, DIAMOND), controle de usuários, registro de aluguéis e geração automática de recibos ao trocar de plano. O projeto é dividido em duas partes principais:

- **Backend REST**: Implementado com Spring, expõe endpoints para gerenciamento de usuários, planos e operações de aluguel.
- **Cliente CLI (linha de comando)**: Permite ao usuário interagir com o sistema diretamente pelo terminal, realizando login, cadastro, troca de plano, aluguel e devolução de bicicletas.

## Funcionalidades

- **Cadastro e autenticação de usuários**
- **Consulta e troca de planos** (com múltiplas opções e benefícios)
- **Cancelamento de planos**
- **Aluguel e devolução de bicicletas**
- **Exclusão de usuário**
- **Geração automática de recibos ao trocar de plano** (armazenados no backend)
- **Consulta de multas, cobranças e status de aluguel**

## Estrutura do Projeto

- `User`, `Plan`, `Bike`: Modelos principais do domínio.
- `UserService`, `ReceiptService`: Serviços centrais que encapsulam a lógica de negócio.
- `UserController`: Endpoints REST para interação com o frontend/CLI.
- `ApiClient`: Cliente HTTP usado pelo CLI para consumir a API backend.
- `MainMenu`, `Payment`: Implementação dos menus de interação e lógica de pagamento/troca de plano no cliente.
- `TerminalUtils`: Utilitário para limpar o terminal e melhorar a experiência do usuário no CLI.

## Como funciona o fluxo básico

1. O usuário acessa o sistema via CLI, faz login ou cadastro.
2. Visualiza ou troca seu plano, realiza aluguéis e devoluções.
3. Ao trocar de plano, o backend gera automaticamente um recibo, que pode ser consultado depois.
4. Todas as operações são persistidas e validadas pelo backend via API REST.

## Diferenciais

- Código Java limpo e didático, ideal para estudos e expansão futura.
- Separação clara entre backend (negócio/armazenamento) e frontend CLI (experiência do usuário).
- Geração automática de recibos, simulando um fluxo real de pagamento.
- Estrutura pensada para fácil manutenção e possíveis integrações (ex: interface web, mobile).

---

**Pronto para alugar sua bike?**  
Clone o projeto, rode o backend e explore todas as funcionalidades pelo terminal!
