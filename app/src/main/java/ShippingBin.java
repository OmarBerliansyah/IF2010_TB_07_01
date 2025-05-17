import java.util.HashMap;
import java.util.Map;

public class ShippingBin {
    private static final int MAX_UNIQUE_ITEMS = 16;

    private static class ShippedItem {
        private Item item;
        private int quantity;

        public ShippedItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public void addQuantity(int amount) {
            this.quantity += amount;
        }

        public int getQuantity() {
            return quantity;
        }

        public Item getItem() {
            return item;
        }

        public int getTotalPrice() {
            return item.getSellPrice() * quantity;
        }
    }

    private Map<String, ShippedItem> bin;

    public ShippingBin() {
        this.bin = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        String name = item.getName();

        //cek udh ada blm
        boolean isNewSlot = !bin.containsKey(name);

        if (isNewSlot && bin.size() >= MAX_UNIQUE_ITEMS) {
            System.out.println("Shipping bin sudah penuh (maksimal 16 jenis item).");
            return;
        }

        if (bin.containsKey(name)) {
            bin.get(name).addQuantity(quantity);
        } else {
            bin.put(name, new ShippedItem(item, quantity));
        }

        System.out.println(quantity + " " + name + " dimasukkan ke shipping bin.");
    }

    public void printShippingBin() {
        if (bin.isEmpty()) {
            System.out.println("Shipping bin kosong.");
        } else {
            System.out.println("Isi Shipping Bin:");
            for (ShippedItem item : bin.values()) {
                System.out.println("- " + item.getItem().getName()
                        + " x" + item.getQuantity()
                        + " (" + item.getItem().getSellPrice() + "G per item)");
            }
        }
    }

    public int processSales(Player player) {
        int total = 0;

        for (ShippedItem shippedItem : bin.values()) {
            int itemTotal = shippedItem.getTotalPrice();
            total += itemTotal;
        }

        player.addGold(total);  //nambah duit
        System.out.println("Hasil penjualan: " + total + "G telah ditambahkan ke saldo.");
        bin.clear(); //kosongin klo 

        return total;
    }

    public boolean isEmpty() {
        return bin.isEmpty();
    }
}
