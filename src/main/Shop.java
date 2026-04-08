package main;

import java.nio.file.Files;
import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;
import model.Client;
import model.Employee;
import model.Ficheros;
import static model.Ficheros.LecturaFichero;

public class Shop {

    private Amount cash = new Amount(100.00);
    private ArrayList<Product> inventory;
    private int numberProducts;
    private ArrayList<Sale> sales;
    static private int cSales;
    static private Amount totalAmount;
    private Employee employee;

    final static double TAX_RATE = 1.04;

    public Shop() {
        inventory = new ArrayList<Product>();
        sales = new ArrayList<Sale>();
    }

    public void initSession() {
        Scanner sc = new Scanner(System.in);

        boolean logged = false;
        while (!logged) {
            System.out.println("Ingresa tu nombre");
            String name = sc.nextLine();
            Employee empleado = new Employee(name);

            System.out.println("Ingresa tu usuario");
            int user = sc.nextInt();
            sc.nextLine();
            System.out.println("Ingresa tu contraseña");
            String pass = sc.nextLine();

            logged = empleado.login(user, pass);
            if (!logged) {

                System.out.println("Usuario o contraseña incorrecto");
            }
        }
        System.out.println("Inicio de sesion exitoso");

    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;
        shop.initSession();
        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) A\u00f1adir producto");
            System.out.println("3) A\u00f1adir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver total");
            System.out.println("9) Eliminar producto del inventario");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;
                case 8:
                    shop.showTotal();
                    break;
                case 9:
//                    shop.deleteProduct();
                    break;

                case 10: //fix issue 1
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        inventory.clear();
        LecturaFichero(inventory);
    }

    public void showTotal() {

        double total = 0.0;
        for (int i = 0; i < cSales; i++) {
            total += sales.get(i).getAmount().getValue();
        }

        System.out.println("El total de ventas: " + total);
    }

    /**
     * show current total cash
     */
    public void showCash() {

        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {

//        if (isInventoryFull()) {
//            System.out.println("No se pueden a\u00f1adir mas productos");
//            return;
//        } 
        System.out.print("Nombre: ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Product product = findProduct(name);
        if (product == null) {
            System.out.print("Precio mayorista: ");
            Double mayorista = scanner.nextDouble();
            Amount wholesalerPrice = new Amount(mayorista);
            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            addProduct(new Product(name, wholesalerPrice, true, stock));

        } else {
            System.out.println("El producto ya existe!");
        }
    }

    /**
     * add stock for a specific product
     */
    public boolean addStock(String nameProduct,int stock) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Seleccione un nombre de producto: ");
//        String name = scanner.next();
        Product product = findProduct(nameProduct);

        if (product != null) {
            // ask for stock
//            System.out.print("Seleccione la cantidad a a\u00f1adir: ");
//            int stock = scanner.nextInt();
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + nameProduct + " ha sido actualizado a " + product.getStock());
            return true;

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + nameProduct);
            return false;
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getPublicPrice());

        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Realizar venta, escribir nombre cliente");
        String nClient = sc.nextLine();

        System.out.println("Ingresa el ID del cliente");
        int idCliente = sc.nextInt();
        sc.nextLine();

        if (Client.MEMBER_ID != idCliente) {
            System.out.println("--------- ID INCORRECTO -------");
            return;
        }

        Client client = new Client(nClient);

        double totalP = 0;
        ArrayList<Product> productsSale = new ArrayList<>();

        while (true) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            String name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);

            if (product != null && product.isAvailable()) {

                totalP += product.getPublicPrice().getValue();
                product.setStock(product.getStock() - 1);

                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }

                productsSale.add(product); // ? GUARDAR PRODUCTO
                System.out.println("Producto añadido con éxito: " + product);

            } else {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        Amount totalAmount = new Amount(totalP);
        cash.setValue(cash.getValue() + totalAmount.getValue());

        // ?? IMPORTANTE: tu clase Sale debe aceptar esto
        Sale sale = new Sale(client, productsSale, totalAmount);

        sales.add(sale);

        System.out.println("Venta realizada con éxito, total: " + totalAmount.getValue());

        if (client.pay(totalAmount)) {
            System.out.println("Su saldo es de: " + client.getBalance().getValue());
        } else {
            System.out.println("Tienes un saldo negativo de: " + client.getBalance().getValue());
        }
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");

        if (sales.isEmpty()) {
            System.out.println("No hay ventas");
            return;
        }

        for (Sale sale : sales) {
            System.out.println(sale);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Quieres exportar las ventas?");
        String resp = sc.nextLine();
        if (resp.equalsIgnoreCase("si")) {
            Ficheros.writeSales(sales);
        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
//        if (isInventoryFull()) {
//            System.out.println("No se pueden a\u00f1adir mas productos, se ha alcanzado el maximo de " + inventory.length);
//            return;
//        }
        inventory.add(product); //Pasa a añadir directamente sin necesidad de tener contador 
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */
//    public boolean isInventoryFull() {
//        if (numberProducts == 10) {
//            return true;
//        } else {
//            return false;
//        }
//    } 
    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        if (inventory.contains(new Product(name))) {
            return inventory.get(inventory.indexOf(new Product(name)));
        } else {
            return null;
        }

    }

    public void deleteProduct(Product product) {
//        Scanner sc = new Scanner(System.in);
        System.out.println("Ingresa el producto que quieres eliminar");
//        String pr = sc.nextLine();
//        Product product = findProduct(pr);

        if (product != null) {
            inventory.remove(product);
            System.out.println("Producto eliminado!");

        } else {
            System.out.println("Producto no encontrado");

        }
    }

    @Override
    public String toString() {
        return "Shop{" + "cash=" + cash + ", inventory=" + inventory + ", numberProducts=" + numberProducts + ", sales=" + sales + ", employee=" + employee + '}';
    }

    public Amount getCash() {
        return cash;
    }

    public void setCash(Amount cash) {
        this.cash = cash;
    }

    private void addStock() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    

}
