import java.util.ArrayList;

public class Mesa{
    boolean status;
    double totalPedido;
    ArrayList<Item> pedido = new ArrayList<Item>();

    public Mesa(){
        this.status = true;
    }

    public void exibir_mesas(ArrayList<Mesa> mesaList){
        for (int i = 0; i < mesaList.size(); i++) {
            if(mesaList.get(i).status){
                System.out.print("Mesa " + (i+1) + " - LIVRE\n");
            }else {
                System.out.print("Mesa " + (i+1) + " - OCUPADA\n");
            }
        }
    }

    public void cria_mesas(ArrayList<Mesa> mesaList){
        for(int i = 0; i < 4; i++){
            mesaList.add(new Mesa());
        }
    }

    public void addItem(int pd, int opt_me, ArrayList<Item> menuList, ArrayList<Mesa> mesaList){
        mesaList.get(opt_me - 1).pedido.add(menuList.get(pd - 1));
        mesaList.get(opt_me - 1).totalPedido += menuList.get(pd - 1).value;

    }

    public void exibPedi(int opt_me, ArrayList<Mesa> mesaList){
        for(int i = 0; i < mesaList.get(opt_me - 1).pedido.size(); i++){
            int qnt_item = 1;
            System.out.println(qnt_item + "x " + mesaList.get(opt_me - 1).pedido.get(i).name + "\tR$" + mesaList.get(opt_me - 1).pedido.get(i).value);
        } 
        System.out.println("\nTotal: " + "R$" + String.format("%.2f",mesaList.get(opt_me - 1).totalPedido));
    }
}