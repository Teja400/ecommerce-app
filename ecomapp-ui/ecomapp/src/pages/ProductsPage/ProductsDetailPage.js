import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';

const ProductDetail = () => {
  if(localStorage.getItem('token') === null)
  {
    window.location.href = '/login';
  }
  const { id } = useParams();
  const [product, setProducts] = useState([]);
  console.log('1. Program Started');
  useEffect(() => {
    // will be called after initial rendering
    // ideal place for api call
    console.log('3. Inside useEffect');
    axios.get('http://localhost:8111/ecomapp/api/products/' + id, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    
    })
      .then((res) => {
        console.log(res.data);
        setProducts(res.data);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        console.log('It is over');
      })
  }, [id]); // dependency list

  useEffect(() => 
  {
    document.title = "Products/Detail";
  })

  console.log('2. Program Ended');

  return (
    <div className = 'products-page'>
      <div className="product-detail d-flex justify-content-center align-items-center" >
        <div class="product-detail-card shadow-lg card ">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="https://t4.ftcdn.net/jpg/05/36/24/13/360_F_536241340_GsrsNhcWC0hyTVaJLilNafyDw6fl0cC8.jpg" class="img-fluid rounded-start border" alt="..." />
            </div>
            <div class="col-md-8 p-0 border border-dark-subtle">
              <div class="card-body">
                <h3 class="card-title text-center mb-5">{product.name}</h3>
                <p class="card-text">{product.description}</p>
                <h6 class="card-text"><small class="">₹{product.price}</small></h6>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    //  <div className = "mt-5 pt-5 text-center">
    //   <h3>Post Detail</h3>
    //     <h4>Product Name: {product.name}</h4>
    //     <p>Product Id: {product.id}</p>
    //     <p>Product price: {product.price}</p>
    //     <p>Product description: {product.description}</p>
    // </div>
  )
}

export default ProductDetail
