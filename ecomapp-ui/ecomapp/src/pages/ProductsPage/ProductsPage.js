import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import handleDelete from './ProductDelete';
import './ProductsPage.css';
import { useCategories } from '../../contexts/CategoryContext';
import { useCart } from '../../contexts/CartContext';

const ProductsPage = () => {
  const [products, setProducts] = useState([]);
  const [categories, loading] = useCategories();
  const [hasProducts, setHasProducts] = useState(true);
  const { addToCart } = useCart();
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  console.log('1. Program Started');
  if (localStorage.getItem('token') === null) {
    window.location.href = '/login';
  }
  useEffect(() => {
    axios.get('http://localhost:8111/ecomapp/api/products', {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then((res) => {
        const productsData = res.data;
        if (productsData.length === 0) {
          console.log('No products found')
          setHasProducts(false);
        }
        else {
          console.log(res.data);
          setProducts(res.data);
        }

      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        console.log('It is over');
      })
  }, []);

  console.log('2. Program Ended')

  useEffect(() => {
    document.title = "Products";
  })

  const filterProducts = () => {
    let filteredProducts = [...products];
    if (minPrice !== '') {
      filteredProducts = filteredProducts.filter(product => product.price >= parseFloat(minPrice));
    }
    if (maxPrice !== '') {
      filteredProducts = filteredProducts.filter(product => product.price <= parseFloat(maxPrice));
    }
    if (minPrice !== '' && maxPrice !== '') {
      filteredProducts = filteredProducts.filter(product => product.price >= parseFloat(minPrice) && product.price <= parseFloat(maxPrice));
    }
    if (searchQuery !== '') {
      filteredProducts = filteredProducts.filter(product => product.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }
    if (minPrice !== '' && maxPrice !== '' && searchQuery !== '') {
      filteredProducts = filteredProducts.filter(product => product.price >= parseFloat(minPrice) && product.price <= parseFloat(maxPrice) && product.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }
    if (minPrice === '' && maxPrice === '' && searchQuery === '') {
      filteredProducts = products;
    }
    return filteredProducts;
  };

  const submitHandlerForFilter = (event) => {
    event.preventDefault();
    setProducts(filterProducts());
  };


  // if (products && products.length === 0) {
  //    console.log('Inside div No products found');
  //   return (

  //     <div className='alert alert-warning mt-5 pt-5'>No products found</div>
  //   )
  // }

  console.log(products);
  return (

    <div className='products-page'>
      <h1 className="products-text text-center mt-5 py-3">Products</h1>
      <div className='pt-3 d-flex align-items-center justify-content-center'>
        <Link to="/products/add" className='btn-responsive btn btn-info border border-dark text-center me-auto ms-3' style={{ width: "12%" }}>Add product</Link>
        <div class="dropdown">
          <button class="ms-3 btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
            Search
          </button>
          <ul class="dropdown-menu">
            <li><Link class="dropdown-item" to = '/products/search'>Search by fields</Link></li>
            <li><Link class="dropdown-item" to="/products/filter">Search Products By Category</Link></li>
          </ul>
        </div>
        <div className='me-3'>
          <form onSubmit={submitHandlerForFilter} className='form d-flex justify-content-center align-items-center'>
            <div className='input-group px-1 rounded me-3 w-50'>
              <label htmlFor="minPrice" className='input-group-text ms-auto'>Min Price:</label>
              <input type="number" id="minPrice" name="minPrice" min="0" step="0.01" value={minPrice} onChange={e => setMinPrice(e.target.value)} className='border border-dark-subtle rounded w-25' />
            </div>
            <div className='input-group px-1 rounded me-3 w-50'>
              <label htmlFor="maxPrice" className='input-group-text'>Max Price:</label>
              <input type="number" id="maxPrice" name="maxPrice" min="0" step="0.01" value={maxPrice} onChange={e => setMaxPrice(e.target.value)} className='border border-dark-subtle rounded w-25' />
            </div>
            <div className='input-group px-1 ms-0 w-25'>
              <label htmlFor="searchQuery" className='input-group-text'>Name:</label>
              <input type="text" id="searchQuery" name="searchQuery" value={searchQuery} onChange={e => setSearchQuery(e.target.value)} className='border border-dark-subtle rounded w-50' />
            </div>
            <button type="submit" className='btn-responsive btn btn-primary ms-1 mt-0'>Filter</button>
          </form>
        </div>
      </div>

      <div className='row p-4 m-0'>
        {hasProducts ? (
          products.map((product) => {
            console.log(product.categoryId);
            let category = product.categoryId;
            if (category === 0) {
              console.log('No Category')
              category = 'No Category';
            }
            else {
              if (!loading) {
                console.log("good response")
                if (category === undefined) category = 'No Category';
                else {
                  const foundCategory = categories.find(c => c.id === category);
                  console.log("inside loading .....")
                  category = foundCategory ? foundCategory.name : 'No Category';
                }
              }
              else {
                category = 'Loading...'
              }
            }
            return (

              <div className='col-md-4 my-2' key={product.id}>
                <div className="card shadow-lg product-card mb-2">
                  <img src="https://t4.ftcdn.net/jpg/05/36/24/13/360_F_536241340_GsrsNhcWC0hyTVaJLilNafyDw6fl0cC8.jpg" className="card-img-top" alt="Card" />
                  <div className="card-body product-card-body">
                    <h5 className="text-center card-title mb-3">{product.name}</h5>
                    <h6 className="card-subtitle mb-4 text-body-secondary text-center">₹{product.price}</h6>
                    <p className="card-text mb-2 text-body-secondary text-truncate"><strong>Description:</strong> {product.description}</p>
                    <p className="card-text  text-body-secondary"><strong>Category: </strong> {category}</p>
                    <div className='d-flex align-items-center justify-content-center'>
                      <Link to={'/products/' + product.id} className="card-link fw-semibold product-link">View</Link>
                      <Link to={'/products/edit/' + product.id} className="card-link fw-semibold product-link">Edit</Link>
                      {(product.categoryId === 0) && (<Link to={'/products/linktocategory/' + product.id} className="card-link fw-semibold me-5 product-link">Link</Link>)}
                      <button className="card-link btn-responsive btn btn-danger ms-auto border border-dark" onClick={() => handleDelete(product.id, products, setProducts)}>Delete</button>
                    </div>
                    <div className="mt-3">
                      <button className="btn btn-responsive btn-warning border border-dark w-100" onClick={() => addToCart(product)}>Add To Cart</button>
                    </div>
                  </div>
                </div>
              </div>
            )
          })) : <div className='alert alert-warning'>No products found</div>}
      </div>
    </div>
  )
}

export default ProductsPage
