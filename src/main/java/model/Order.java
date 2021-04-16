package model;

import java.sql.Date;

public class Order {
   private int id;
   private int client;
   private int product;
   private int amount;
   private int price;
   private Date date;

   public Order() { }

   public Order(int client, int product, int amount, int price, Date date) {
      this.client = client;
      this.product = product;
      this.amount = amount;
      this.price = price;
      this.date = date;
   }

   public Order(int id, int client, int product, int amount, int price, Date date) {
      this.id = id;
      this.client = client;
      this.product = product;
      this.amount = amount;
      this.price = price;
      this.date = date;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getClient() {
      return client;
   }

   public void setClient(int client) {
      this.client = client;
   }

   public int getProduct() {
      return product;
   }

   public void setProduct(int product) {
      this.product = product;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }
}
