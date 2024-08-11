import React from 'react'
import { Link } from 'react-router-dom';
import '../Header/Header.css';
const Banner = () => {
  return (
    <section>
   <div className="container col-xxl-8 px-4 py-2">
    <div className="row flex-lg-row-reverse align-items-center g-5 py-5 mt-2">
      <div className="col-10 col-sm-8 col-lg-6">
        <img src="https://www.salesforce.com/blog/wp-content/uploads/sites/2/2023/11/SF_Blog_Image_Ecommerce_Changing_Everything.png" className="banner d-block mx-lg-auto img-fluid rounded" alt="Bootstrap Themes" width="1500" height="500" loading="lazy"/>
      </div>
      <div className="col-lg-6">
        <h1 className="display-5 fw-bold text-body-emphasis lh-1 mb-3">Welcome to E-Commerce App</h1>
        <p className="lead">Login to use the website in its full capacity</p>
        <div className="d-grid gap-3 d-md-flex justify-content-center me-5">
          <Link className="border border-dark btn btn-primary btn-lg px-4 ms-5" to = "/login">Login</Link>
          <Link className="border border-dark btn btn-outline-secondary btn-lg px-4" to = "/registration">Register</Link>
        </div>
      </div>
    </div>
  </div>
    </section>
  )
}

export default Banner;
