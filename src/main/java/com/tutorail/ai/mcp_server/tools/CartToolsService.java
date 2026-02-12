package com.tutorail.ai.mcp_server.tools;

import com.tutorail.ai.mcp_server.entity.Cart;
import com.tutorail.ai.mcp_server.entity.User;
import com.tutorail.ai.mcp_server.repository.CartRepository;
import com.tutorail.ai.mcp_server.repository.UserRepository;
import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpResource;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartToolsService {

    @Autowired
    private CartRepository repository;

    @Autowired
    private UserRepository userRepository;

    @McpTool(name = "addToCart", description = "Adds a product to the shopping cart. If the product already exists in the cart, increases the quantity and updates the total price proportionally. If it's a new product, saves it to the cart. Returns the updated cart item.")
    public Cart addToCart(@McpToolParam(description = "The cart item object containing product details to add, including productId, productName, price, and quantity", required = true) Cart cart){
        Cart product = repository.findByProductId(cart.getProductId());
        if (product!=null){ // found existing one, just increase quantity
            double v = product.getPrice() / product.getQuantity();
            product.setQuantity(product.getQuantity()+cart.getQuantity());
            product.setPrice(product.getQuantity()*v);
            return product;
        }
        repository.save(cart);
        return cart;
    }

    @McpTool(name = "removeFromCart", description = "Removes a product from the shopping cart using the product ID. Returns a success message with the product ID or an error message if the product doesn't exist in the cart.")
    public String deleteFromCart(@McpToolParam(description = "The unique identifier of the product to remove from the cart", required = true) String productId){
        if (repository.findByProductId(productId)==null){
            return "Product Id doesn't exists, please check your id";
        }
        repository.deleteByProductId(productId);
        return productId + " product id has been deleted successfully";
    }

    @McpTool(name = "getCartItem", description ="Retrieves a specific cart item by product ID. Returns the cart item details including product ID, name, price, and quantity, or null if the product doesn't exist in the cart.")
    public Cart findByProductId(@McpToolParam(description = "The unique identifier of the product to look up in the cart") String productId){
        if (repository.findByProductId(productId)==null){
            return null;
        }
       return repository.findByProductId(productId);
    }

    @McpTool(name = "getAllCartItems", description ="Retrieves all items currently in the shopping cart. Returns a list of cart items with their details including product ID, name, price, and quantity.")
    public List<Cart> getAllCartItems(){
        return repository.findAll();
    }

    @McpTool(name = "clearCart",
            description = "Removes all items from the shopping cart. Returns a confirmation message.")
    public String clearCart() {
        repository.deleteAll();
        return "All items in the cart has been removed";
    }

    @McpTool(name = "getCartTotal",
            description = "Calculates and returns the total price of all items in the shopping cart.")
    public double getCartTotal() {
        return repository.findAll().stream().mapToDouble(Cart::getPrice).sum();
    }

    @McpResource(uri = "user-profile://{username}", name = "User Profile", description = "Provides User Profile Information")
    public McpSchema.ReadResourceResult getUserProfile(String username){
        Optional<User> user = userRepository.findByName(username);
        return new McpSchema.ReadResourceResult(List.of(new McpSchema.TextResourceContents(
                "user-profile://" + username,
                "application/json",
                "fetched user profile from DB",
                Map.of("User Details",user.get())
        )));
    }
}
