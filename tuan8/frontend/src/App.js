import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const PRODUCT_SERVICE = process.env.REACT_APP_PRODUCT_SERVICE || 'http://localhost:8081';
const CART_SERVICE = process.env.REACT_APP_CART_SERVICE || 'http://localhost:8082';
const ORDER_SERVICE = process.env.REACT_APP_ORDER_SERVICE || 'http://localhost:8083';

function App() {
  const userId = 'user-' + Math.random().toString(36).substr(2, 9);
  const [products, setProducts] = useState([]);
  const [cart, setCart] = useState({ items: [], total: 0 });
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadProducts();
    loadCart();
    loadOrders();
  }, []);

  const loadProducts = async () => {
    try {
      setLoading(true);
      const response = await axios.get(`${PRODUCT_SERVICE}/api/products`);
      console.log('Products loaded:', response.data.data);
      setProducts(response.data.data || []);
      setMessage('✓ Products loaded from Redis');
    } catch (error) {
      setMessage('✗ Error loading products: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const loadCart = async () => {
    try {
      const response = await axios.get(`${CART_SERVICE}/api/cart/${userId}`);
      setCart(response.data.data || { items: [], total: 0 });
    } catch (error) {
      console.error('Error loading cart:', error);
    }
  };

  const loadOrders = async () => {
    try {
      const response = await axios.get(`${ORDER_SERVICE}/api/orders/user/${userId}`);
      setOrders(response.data.data || []);
    } catch (error) {
      console.error('Error loading orders:', error);
    }
  };

  const addToCart = async (product) => {
    try {
      setLoading(true);
      const response = await axios.post(
        `${CART_SERVICE}/api/cart/${userId}/add`,
        {
          productId: product.id,
          productName: product.name,
          price: product.price,
          quantity: 1
        }
      );
      setCart(response.data.data);
      setMessage(`✓ ${product.name} added to cart`);
    } catch (error) {
      setMessage('✗ Error adding to cart: ' + error.message);
    } finally {
      setLoading(false);
    }
  };

  const checkout = async () => {
    if (cart.items.length === 0) {
      setMessage('✗ Cart is empty');
      return;
    }

    try {
      setLoading(true);
      const response = await axios.post(`${ORDER_SERVICE}/api/checkout/validated`, {
        userId,
        cartData: cart
      });

      setMessage(`✓ Order placed! Order ID: ${response.data.data.orderId}`);
      
      // Clear cart
      await axios.post(`${CART_SERVICE}/api/cart/${userId}/clear`);
      setCart({ items: [], total: 0 });
      
      // Reload orders
      await loadOrders();
    } catch (error) {
      setMessage('✗ Checkout failed: ' + (error.response?.data?.message || error.message));
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <header>
        <h1>🛍️ E-Commerce Platform</h1>
        <p className="user-id">User ID: <code>{userId}</code></p>
      </header>

      <div className="container">
        <div className="section products-section">
          <h2>📦 Products (from Redis)</h2>
          {message && <div className="message">{message}</div>}
          {loading && <p className="loading">Loading...</p>}
          <div className="products-grid">
            {products.map(product => (
              <div key={product.id} className="product-card">
                <h3>{product.name}</h3>
                <p className="price">${product.price.toFixed(2)}</p>
                <p className="category">{product.category}</p>
                <button 
                  onClick={() => addToCart(product)}
                  disabled={loading}
                  className="btn-add"
                >
                  Add to Cart
                </button>
              </div>
            ))}
          </div>
        </div>

        <div className="section cart-section">
          <h2>🛒 Shopping Cart</h2>
          {cart.items.length === 0 ? (
            <p className="empty">Cart is empty</p>
          ) : (
            <>
              <div className="cart-items">
                {cart.items.map(item => (
                  <div key={item.cartItemId} className="cart-item">
                    <div>
                      <strong>{item.productName}</strong>
                      <p>${item.price} x {item.quantity} = ${(item.price * item.quantity).toFixed(2)}</p>
                    </div>
                  </div>
                ))}
              </div>
              <div className="cart-total">
                <strong>Total: ${cart.total.toFixed(2)}</strong>
              </div>
              <button 
                onClick={checkout}
                disabled={loading}
                className="btn-checkout"
              >
                Checkout
              </button>
            </>
          )}
        </div>

        <div className="section orders-section">
          <h2>📋 Orders</h2>
          {orders.length === 0 ? (
            <p className="empty">No orders yet</p>
          ) : (
            <div className="orders-list">
              {orders.map(order => (
                <div key={order.orderId} className="order-card">
                  <p><strong>Order ID:</strong> {order.orderId}</p>
                  <p><strong>Status:</strong> <span className="status">{order.status}</span></p>
                  <p><strong>Total:</strong> ${order.total.toFixed(2)}</p>
                  <p><strong>Items:</strong> {order.items.length}</p>
                  <p><strong>Date:</strong> {new Date(order.createdAt).toLocaleString()}</p>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <footer>
        <p>🚀 Microservices: Product (8081) | Cart (8082) | Order (8083) | Inventory (8084) | Redis (6379)</p>
      </footer>
    </div>
  );
}

export default App;
