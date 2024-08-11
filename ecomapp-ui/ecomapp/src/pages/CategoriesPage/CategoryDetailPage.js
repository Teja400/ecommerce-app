import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';

const CategoryDetail = () => {
  
  if(localStorage.getItem('token') === null)
  {
      window.location.href = '/login';
  }
  const { id } = useParams();
  const [category, setCategory] = useState([]);
  console.log('1. Program Started');
  useEffect(() => {

    // will be called after initial rendering
    // ideal place for api call
    console.log('3. Inside useEffect');
    axios.get('http://localhost:8111/ecomapp/api/categories/' + id, {
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    })
      .then((res) => {
        console.log(res.data);
        setCategory(res.data);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => {
        console.log('It is over');
      })
  }, [id]); // dependency list

  useEffect(() => {
    document.title = "Categories/Detail";
  }
  )

  console.log('2. Program Ended');

  return (
    <div className = 'categories-page'>
      <div className="category-detail d-flex justify-content-center align-items-center" >
        <div class="category-detail-card shadow-lg card ">
          <div class="row g-0">
            <div class="col-md-4">
              <img src="https://t4.ftcdn.net/jpg/05/36/24/13/360_F_536241340_GsrsNhcWC0hyTVaJLilNafyDw6fl0cC8.jpg" class="img-fluid rounded-start border" alt="..." />
            </div>
            <div class="col-md-8 p-0 border border-dark-subtle">
              <div class="card-body">
                <h3 class="card-title text-center mb-5">{category.name}</h3>
                <p class="card-text">Id : {category.id}</p>
                <p class="card-text">{category.description}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    //  <div className = "mt-5 pt-5 text-center">
    //   <h3>Post Detail</h3>
    //     <h4>category Name: {category.name}</h4>
    //     <p>category Id: {category.id}</p>
    //     <p>category price: {category.price}</p>
    //     <p>category description: {category.description}</p>
    // </div>
  )
}

export default CategoryDetail
