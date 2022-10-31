package JavaActivity;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Home {

    private Scanner in = new Scanner(System.in);
    private String confirmPassCheck;
    private User[] userAccounts;
    public User currentUser;

    // simula id da posição do vetor, onde estão armazenados os dados
    private int idAccountInfo;

    public Home() {
        idAccountInfo = 0;
        userAccounts = new User[1000];
    }

    // método para auxiliar nas buscas de um usuário
    public User findUserById(String login) {

        for (int i = 0; i <= idAccountInfo; i++) {
            if (login.equals(userAccounts[i].getLogin())) {
                return userAccounts[i];
            }
        }
        return null;
    }

    // checa se o login já existe
    public boolean checkLoginAlreadyExist(String login) {

        // predefinido como não existente
        boolean loginExist = false;

        for (int i = 0; i < idAccountInfo; i++) {
            if (login.equals(userAccounts[i].getLogin())) {
                loginExist = true; // login já existe
                break;
            }
        }
        return loginExist;
    }

    // checa se a senha é a do login correto
    public boolean checkPassIsSuitableOfLogin(String login, String password) {

        User user = this.findUserById(login);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    // edita a senha
    public void changeLogin(String login, String storageNewLogin) {
        User user = this.findUserById(login);
        user.setLogin(storageNewLogin);
    }

    // edita a senha
    public void changePass(String login, String storageNewPass) {
        User user = this.findUserById(login);
        user.setPassword(storageNewPass);
    }

    // tela principal
    public void mainTitle() {

        boolean executionSwitch = false;
        while (!executionSwitch) {
            Scanner in = new Scanner(System.in);

            System.out.println("|  1 - Cadastre-se  |  2 - Login  |  3 - Finalizar App  | ");

            try {
                switch (in.nextInt()) {
                    case 1:
                        this.createNewAccount();
                        this.menuWhenUserLogged();
                        break;

                    case 2:
                        this.login();
                        this.menuWhenUserLogged();
                        break;

                    case 3:
                        System.out.println("Saindo...");
                        Runtime.getRuntime().exit(0);

                    default:
                        System.out.println("Opção inválida, tente novamente: ");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: " + e);
            }
        }
    }

    // cria conta para um novo usuário
    public void createNewAccount() {

        String login;
        String password;

        // criação de login
        System.out.println("Crie um Login: ");
        login = in.nextLine();

        // verifica se está vazio
        while (login.length() == 0) {
            System.out.println("Erro: O campo login está vazio! Preencha-o novamente: ");
            login = in.nextLine();
        }

        // verifica se login já existe
        while (this.checkLoginAlreadyExist(login)) {
            System.out.println("Erro: A conta já existe! Tente outro: ");
            login = in.nextLine();
        }

        // criação de senha
        System.out.println("Crie uma senha: ");
        password = in.nextLine();

        while (password.length() == 0) {
            System.out.println("Campos vazios não são permitidos! Tente novamente: ");
            password = in.nextLine();
        }

        // pede confirmação de senha para checar se foi digitada corretamente
        System.out.println("Confirme sua senha: ");
        confirmPassCheck = in.nextLine();

        while (!password.equals(confirmPassCheck)) {
            System.out.println("A senha está diferente! Tente novamente: ");
            confirmPassCheck = in.nextLine();
        }

        System.out.println("Registrando e redirecionando... ");
        currentUser = this.registerNewAccount(login, password);
    }

    // registra na array o usuário criado
    public User registerNewAccount(String login, String password) {
        User accountForRegister = new User(login, password);
        userAccounts[idAccountInfo] = accountForRegister;
        idAccountInfo++;

        return accountForRegister;
    }

    // login para usuários já cadastrados
    public void login() {

        String login;
        String password;

        System.out.println("Seu login: ");
        login = in.nextLine();

        while (!this.checkLoginAlreadyExist(login)) {
            System.out.println("Esse login não existe, tente outro: ");
            login = in.nextLine();
        }

        System.out.println("Sua Senha: ");
        password = in.nextLine();

        while (password.equals("")) {
            System.out.println("Campos vazios não são permitidos! Tente novamente: ");
            password = in.nextLine();
        }

        while (!this.checkPassIsSuitableOfLogin(login, password)) {
            System.out.println("Senha incorreta! Tente novamente: ");
            password = in.nextLine();
        }
        currentUser = this.findUserById(login);
    }

    // menu de quando usuário já estiver logado
    public void menuWhenUserLogged() {

        boolean executionSwitch = false;

        while (!executionSwitch) {
            Scanner in = new Scanner(System.in);

            System.out.println("| O que deseja fazer " + currentUser.getLogin() + "? ");
            System.out.println("|  1 - Editar Perfil  |  2 - Conversas  |  3 - Deslogar  |");

            try {
                switch (in.nextInt()) {

                    case 1: // edição de perfil
                        editProfile();
                        break;

                    case 2: // sistema de mesnagem
                        messageSystem();
                        break;

                    case 3: // volta ao menu
                        mainTitle();
                        break;

                    default:
                        System.out.println("Opção inválida, tente novamente: ");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: " + e);
            }
        }
    }

    // poder editar login e senha do usuário
    public void editProfile() {

        System.out.println("| O que deseja alterar " + currentUser.getLogin() + "?");
        System.out.println("|  1 - Seu Login  |  2 - Sua Senha  |  3 - Sair  | ");
        int optionChoosed = in.nextInt();
        in.nextLine();

        while (optionChoosed != 3) {
            switch (optionChoosed) {

                case 1:
                    System.out.println("Digite o novo Login: ");
                    String storageNewLogin = in.nextLine();
                    this.changeLogin(currentUser.getLogin(), storageNewLogin);
                    System.out.println("Login alterado com sucesso!");
                    break;

                case 2:
                    System.out.println("Digite a nova Senha: ");
                    String storageNewPass = in.nextLine();
                    this.changePass(currentUser.getLogin(), storageNewPass);
                    System.out.println("Senha alterada com sucesso!");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente: ");
            }
            this.editProfile();
        }
        this.menuWhenUserLogged();
    }

    // sistema de mensagens
    public void messageSystem() {

        String login;
        String message;

        System.out.println("| Inbox de " + currentUser.getLogin());
        System.out.println("|  1 - Enviar Mensagens  |  2 - Mensagens Recebidas  |  3 - Voltar   |");
        int optionChoosed = in.nextInt();
        in.nextLine();

        while (optionChoosed != 3) {
            switch (optionChoosed) {

                case 1:
                    System.out.println("Digite o Login do seu amigo: ");
                    login = in.nextLine();

                    while (!this.checkLoginAlreadyExist(login)) {
                        System.out.println("Amigo não encontrado! Tente outro: ");
                        login = in.nextLine();
                    }

                    System.out.println("Amigo encontrado!");
                    System.out.println("Digite o que deseja enviar: ");
                    message = in.nextLine();

                    Messages messageSended = new Messages(login, currentUser.getLogin(), message);
                    currentUser.addMessage(messageSended);
                    User user = findUserById(login);
                    user.addMessage(messageSended);
                    System.out.println("Mensagem enviada!");
                    break;

                case 2:
                    Messages[] receivedMessage = currentUser.getMessage();
                    if (receivedMessage != null) {
                        for (int i = 0; i < currentUser.countMessages; i++) {
                            Messages messages = receivedMessage[i];
                            System.out.println("Mensagem de " + findUserById(messages.getFromCurrentUser()).getLogin() + " para você! ");
                            System.out.println("> " + messages.getCurrentMessage());
                        }
                    } else {
                        System.out.println("Vá procurar amigos! a caixa está vazia ;-; ");
                    }
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente: ");
                    break;
            }
            messageSystem();
        }
        menuWhenUserLogged();
    }
}
