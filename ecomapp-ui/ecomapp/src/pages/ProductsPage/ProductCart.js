import React, { useEffect } from 'react'
import { useCart } from '../../contexts/CartContext';

const ProductCart = () => {
    useEffect(() => {
        document.title = "Products/Cart";
    })
    const {cart, removeFromCart} = useCart();
    console.log(cart)
    return (
        <div>
            <div class="products-page shadow-lg card mt-5 pt-5">
                <h5 class="cart-header card-header">Cart Items</h5>
                {cart.length !== 0 ? cart.map((product) => {
                    return <div class="cart-body card-body border-bottom">
                        <div class = 'd-flex'>
                        <h5 class="card-title">{product.name}</h5>
                        <h5 class="card-text ms-auto me-4"><small class="">₹{product.price}</small></h5>
                        </div>
                        <div class = 'd-flex'>
                        <p class="card-text ms-0 me-4"><small class="">CategoryId: {product.categoryId}</small></p>
                            <button class="btn btn-danger ms-auto me-3" onClick={() => removeFromCart(product)}>Remove</button>
                        </div>
                </div>
                }) : <div class="alert alert-warning">No Items in cart</div>}
            </div>
        </div>
    )
}

export default ProductCart
