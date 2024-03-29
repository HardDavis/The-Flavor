import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        Mesa mesas = new Mesa();
        Menu menu = new Menu();
        /* Progesso po = new Progesso(); */
        int opt, opt_me;

        ArrayList<Mesa> mesaList = new ArrayList<>();
        ArrayList<Item> menuList = new ArrayList<>();

        mesas.cria_mesas(mesaList);
        menu.cria_itens(menuList);

        String opcoes = "1. Consultar menu\n" +
                "2. Fazer pedido/abrir conta\n" +
                "3. Verificar pedido\n" +
                "4. Fechar conta\n" +
                "\n0. Voltar";

        Uteis.limpa_term();

        do {
            mesas.exibir_mesas(mesaList); // EXIBE MESAS

            System.out.print("\nEscolha a mesa: "); // ESCOLHA DE MESA
            opt_me = scan.nextInt();

            Uteis.limpa_term();

            if (opt_me > 0 && opt_me <= mesaList.size()) {
                Mesa mesa = mesaList.get(opt_me - 1);
                boolean aux = true;
                Boolean volta = false;

                Uteis.limpa_term();
                do {
                    System.out.println(opcoes);

                    System.out.print("\nEscolha uma opção: ");
                    opt = scan.nextInt();
                    Uteis.limpa_term();

                    // SELEÇÃO DAS OPÇÕES A PARTIR DAQUI
                    if (opt == 1) { // --> 1. EXIBE MENU
                        Uteis.limpa_term();
                        do {
                            menu.exibir_menu(menuList);

                            System.out.print(
                                    "\nDigite o número do item para mostrar suas informações ou 0 para voltar: ");
                            opt = scan.nextInt();

                            Uteis.limpa_term();
                            if (opt > 0 && opt <= menuList.size()) {// --> EXIBI AS INFORÇÕES DO ITEM SELECIONADO
                                System.out.println(menuList.get(opt - 1));
                            } else if (opt < 0 || opt > menuList.size()) {
                                System.out.println("Opção Inválida\n");
                            }

                        } while (opt > 0 && opt <= menuList.size());

                    } else if (opt == 2) { // --> 2. FAZER PEDIDO/ABRIR CONTA
                        Uteis.limpa_term();
                        int pd;

                        if (mesa.pedidos.size() == 0 || mesa.pedidos.get(mesa.pedidos.size() - 1).pedidoFinal) {
                            mesa.pedidos.add(new Pedido());
                        }

                        Pedido ped_sec = mesa.pedidos.get(mesa.pedidos.size() - 1);

                        do {

                            System.out.println(ped_sec.exibPedi(menuList));
                            if (ped_sec.pedido.size() != 0) {
                                System.out.println(
                                        "1. Adicionar item\n2. Remover item\n3. Finalizar pedido\n4. Cancelar pedido\n\n0 - Voltar");

                                System.out.print("\nEscolha uma opção: ");
                                opt = scan.nextInt();
                            } else if (ped_sec.pedido.size() == 0 && volta == false) {
                                opt = 1;
                            }

                            if (opt == 1) { // 1. ADICIONA ITEM
                                Uteis.limpa_term();
                                do {
                                    System.out.println(ped_sec.exibPedi(menuList));
                                    menu.exibir_menu(menuList);

                                    System.out.print("\nEscolha o item a ser adicionado ao pedido: ");
                                    pd = scan.nextInt();
                                    Uteis.limpa_term();

                                    if (pd > 0 && pd <= menuList.size()) {
                                        ped_sec.addItem(pd, menuList);
                                        volta = true;
                                        
                                        
                                    } else if (pd == 0 && !volta) {
                                        opt = 0;
                                        break;
                                    } else if (pd < 0 || pd > menuList.size()) {
                                        System.out.println("Opção Inválida\n");
                                    }
                                } while (pd != 0);

                            } else if (opt == 2) { // 2. REMOVE ITEM
                                Uteis.limpa_term();

                                menu.exibir_menu(menuList);

                                do {
                                    if (menuList.size() > 0) {
                                        System.out.print("\nQual item deseja remover: ");
                                        pd = scan.nextInt();

                                        if (pd == 0 || ped_sec.pedido.size() == 0) {
                                            opt = 0;
                                            break;
                                        } else if (pd > 0 && pd <= menuList.size()) {
                                            mesa.pedidos.get(mesa.pedidos.size() - 1);
                                            ped_sec.remov_item(pd, menuList);
                                            mesa.setTotalPedido();
                                            break;
                                        } else if (pd < 0 || pd > menuList.size()) {
                                            System.out.println("Opção inválida.");
                                        }
                                    } else {
                                        System.out.println("A lista está vazia.");
                                        break;
                                    }

                                } while (pd != 0 || ped_sec.pedido.size() != 0);

                            } else if (opt == 3) { // 3. FINALIZAR PEDIDO
                                Uteis.limpa_term();
                                mesa.setTotalPedido();
                                mesa.pedidos.get(mesa.pedidos.size() - 1).pedidoFinal = true;
                                mesa.mudaStts();

                                opt = 0;

                            } else if (opt == 4) { // 4. CANCELAR PEDIDO
                                Uteis.limpa_term();
                                ped_sec.cancelPedido();
                                mesa.setTotalPedido();
                                opt = 0;

                            } else if (opt > 4 || opt < 0) { // OPÇÕES INVÁLIDAS
                                Uteis.limpa_term();
                                System.out.println("\nOpção inválida\n");
                            }

                        } while (opt != 0);

                    } else if (opt == 3) { // --> 3. VERIFICAR PEDIDOS.
                        if (mesa.pedidos.size() > 0) {
                            Pedido ped_sec = mesa.pedidos.get(mesa.pedidos.size() - 1);
                            Uteis.limpa_term();
                            mesa.setTotalPedido();
                            System.out.println(ped_sec.exibPedi(menuList));
                        } else {
                            Uteis.limpa_term();
                            System.out.println("Nenhum pedido foi realizado.\n");
                        }

                    } else if (opt == 4) { // --> 4. FECHAR CONTA.
                        Uteis.limpa_term();
                        boolean pag_stts = mesa.fecharConta(menuList);

                        if (pag_stts) {
                            aux = false;
                        }

                    } else if (opt == 0) { // --> VOLTAR PARA SELEÇÃO DE MESA.
                        aux = false;
                    } else {
                        System.out.println("Opção Inválida\n");
                    }
                } while (aux);
            } else if (opt_me < 0 || opt_me > mesaList.size()) {
                System.out.println("Opção Inválida\n");
            }

        } while (opt_me != 0); // --> ENCERRAR PROGRAMA

        scan.close();
    }
}