import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import handleDelete from '../CategoriesPage/CategoryDelete';
import './CategoriesPage.css';
import axios from 'axios';

const CategoriesPage = () => {

  const [categories, setCategories] = useState([]);
  const [hasCategories, setHasCategories] = useState(true);
  const [searchQuery, setSearchQuery] = useState('');

  if (localStorage.getItem('token') === null) {
    window.location.href = '/login';
  }

  useEffect(() => {
    document.title = "Categories";
    axios.get('http://localhost:8111/ecomapp/api/categories', {
      headers:
      {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then((res) => {
        const categoriesData = res.data;
        if (categoriesData.length === 0) {
          console.log('No categories found')
          setHasCategories(false);
        }
        else {
          console.log(res.data);
          setCategories(res.data);
        }

      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        console.log('It is over');
      })
  }, []);

  const filterCategories = () => {
    let filteredCategories = [...categories];
    if (searchQuery !== '') {
      filteredCategories = filteredCategories.filter(product => product.name.toLowerCase().includes(searchQuery.toLowerCase()));
    }
    return filteredCategories;
  };

  const submitHandlerForFilter = (event) => {
    event.preventDefault();
    setCategories(filterCategories());
  };

  

  // if (categories && categories.length === 0) {
  //   return (
  //     <div className='alert alert-warning'>No products found</div>
  //   )
  // }
  // console.log(categories);

  return (

    <div className='categories-page mt-3'>
      <h1 className="categories-text text-center mt-5 py-4">Categories</h1>
      <div className="pt-2 d-flex justify-content-center">
        <Link to="/categories/add" className='btn btn-responsive btn-info border border-dark text-center ms-4' style={{ width: "12%" }}>Add Category</Link>
        <Link to="/categories/search" className='btn btn-responsive btn-info border border-dark text-center ms-3 me-auto' style={{ width: "9%" }}>Search</Link>
        <div className='me-3'>
          <form onSubmit={submitHandlerForFilter} className = 'form d-flex justify-content-center align-items-center'>
            <div className='input-group px-1 ms-auto w-75'>
              <label htmlFor="searchQuery" className='input-group-text'>Name:</label>
              <input type="text" id="searchQuery" name="searchQuery" value={searchQuery} onChange={e => setSearchQuery(e.target.value)} className= 'border border-dark-subtle rounded w-50'/>
            </div>
            <button type="submit" className='btn btn-responsive btn-primary  mt-0'>Filter</button>
          </form>
          </div>
      </div>
      <div className='row p-4 m-0'>

        {hasCategories ? (categories.map((category) => {
          return (

            <div className='col-md-4 my-2' key={category.id}>
              <div className="shadow-lg card category-card mb-2">
                <img src="https://t4.ftcdn.net/jpg/05/36/24/13/360_F_536241340_GsrsNhcWC0hyTVaJLilNafyDw6fl0cC8.jpg" className="card-img-top" alt="Card" />
                <div className="card-body category-page-card">
                  <h5 className="text-center card-title mb-3">{category.name}</h5>
                  <p className="card-text mb-2 text-body-secondary text-truncate"><strong>Description:</strong> {category.description}</p>
                  <div className='d-flex align-items-center'>
                    <Link to={'/categories/' + category.id} className="card-link fw-bold category-link">View</Link>
                    <Link to={'/categories/edit/' + category.id} className="card-link fw-bold category-link">Edit</Link>
                    <button className="card-link btn btn-responsive btn-danger ms-auto border border-dark" onClick={() => handleDelete(category.id, categories, setCategories)}>Delete</button>
                  </div>
                  {/* <div className = "d-flex mt-2 ms-0">
                    <button className="card-link btn btn-primary" onClick = {() => addToCart(product)}>Add To Cart</button>
                    <button className="card-link btn btn-danger" onClick = {() => removeFromCart(product)}>Remove from Cart</button>
                    </div> */}

                </div>
              </div>
            </div>
          )
        })) : <div className='alert alert-warning'>No categories found</div>}
      </div>


    </div>
  )
}

export default CategoriesPage
