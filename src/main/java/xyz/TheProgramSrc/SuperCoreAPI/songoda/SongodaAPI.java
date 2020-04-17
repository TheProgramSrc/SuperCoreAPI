/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.songoda;

public class SongodaAPI {

    private final String productsApiURL;
    private String token;
    private final SongodaInjector songodaInjector;
    private SongodaProduct songodaProduct;

    public SongodaAPI(String token){
        this.productsApiURL = "https://songoda.com/api/v2/products/id/{ProductID}";
        this.songodaInjector = new SongodaInjector();
        this.token = token;
    }

    public SongodaAPI(){
        this(null);
    }

    public String getProductsApiURL() {
        return productsApiURL;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SongodaInjector getSongodaInjector() {
        return songodaInjector;
    }

    public SongodaProduct getSongodaProduct(String productId){
        if(this.songodaProduct == null){
            this.songodaProduct = new SongodaProduct(this, productId);
        }
        return songodaProduct;
    }
}
