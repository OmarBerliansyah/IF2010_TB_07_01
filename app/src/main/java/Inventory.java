import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private static class InventoryItem {
        private Item item;
        private int quantity;

        public InventoryItem(Item item, int quantity) {
            this.item = item;
            this.quantity = quantity;
        }

        public Item getItem() {
            return item;
        }

        public int getQuantity() {
            return quantity;
        }

        public void addQuantity(int amount) {
            this.quantity += amount;
        }

        public void subtractQuantity(int amount) throws IllegalArgumentException {
            if (this.quantity < amount) {
                throw new IllegalArgumentException("Jumlah item tidak cukup.");
            }
            this.quantity -= amount;
        }
    }

    private HashMap<String, InventoryItem> inventory;

    public Inventory() {
        this.inventory = new HashMap<>();

        //ubah item pake subclass
        addItem(new Item("Parsnips Seeds"), 15);
        addItem(new Item("Hoe"), 1);
        addItem(new Item("Watering Can"), 1);
        addItem(new Item("Pickaxe"), 1);
        addItem(new Item("Fishing Rod"), 1);
    }

    public void addItem(Item item, int quantity) {
        String name = item.getName();
        if (inventory.containsKey(name)) {
            inventory.get(name).addQuantity(quantity);
        } else {
            inventory.put(name, new InventoryItem(item, quantity));
        }
    }

    public void removeItem(String name, int quantity) {
        InventoryItem entry = inventory.get(name);
        if (entry == null) {
            System.out.println("Item tidak ditemukan.");
            return;
        }

        try {
            entry.subtractQuantity(quantity);
            if (entry.getQuantity() == 0) {
                inventory.remove(name);
                System.out.println(name + " dihapus dari inventory.");
            } else {
                System.out.println("Item " + name + " dikurangi " + quantity);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal mengurangi item: " + e.getMessage());
        }
    }

    public Item getItem(String name) {
        InventoryItem entry = inventory.get(name);
        return entry != null ? entry.getItem() : null;
    }

    public int getQuantity(String name) {
        InventoryItem entry = inventory.get(name);
        return entry != null ? entry.getQuantity() : 0;
    }

    public boolean hasItem(String name, int quantity) {
        InventoryItem entry = inventory.get(name);
        return entry != null && entry.getQuantity() >= quantity;
    }

    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        for (InventoryItem entry : inventory.values()) {
            itemList.add(entry.getItem());
        }
        return itemList;
    }

    public void printInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory kosong.");
        } else {
            System.out.println("Isi Inventory:");
            for (InventoryItem entry : inventory.values()) {
                System.out.println("- " + entry.getItem().getName() + " x" + entry.getQuantity());
            }
        }
    }
}
