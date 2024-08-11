import React, { createContext, useContext, useState } from "react";

// Step 1: Create context -- so that data can be supplied through it
const CartContext = createContext();

// Step 2: thru the CartProvider component -- we can supply the data
export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  const addToCart = (product) => {
    console.log("Adding to cart", product);
    setCart((current) => [...current, product]);
  };

  const removeFromCart = (product) => {
    setCart((current) => {
      const indexOfProduct = current.findIndex((p) => p.id === product.id);
      if (indexOfProduct === -1) {
        return current;
      }
      return [
        ...current.slice(0, indexOfProduct),
        ...current.slice(indexOfProduct + 1),
      ];
    });
  }

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart }}>
      {children}
    </CartContext.Provider>
  );
};

// useContext facilitates the context available in the components
// custom hook
export const useCart = () => useContext(CartContext);