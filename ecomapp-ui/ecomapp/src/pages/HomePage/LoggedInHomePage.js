import React, { useEffect } from 'react'
import { Link } from 'react-router-dom'

const LoggedInHomePage = () => {
    return (useEffect(() => {
        document.title = "Home";
    }, []),
        <div className='d-flex justify-content-center align-items-center'>
            <div className='w-75'>
                <div className="container col-xxl-8 px-4 py-2">
                    <div className="shadow-lg row flex-lg-row-reverse align-items-center g-5 pb-4 mt-3 px-3 rounded" width="200">
                        <div className="col-10 col-sm-8 col-lg-6">
                            <img src="https://img.freepik.com/premium-vector/mobile-smart-phone-with-shop-app-small-people-flying-around-online-shopping-concept_3482-9086.jpg" className="banner d-block mx-lg-auto img-fluid rounded-circle" alt="Bootstrap Themes" width="1500" height="500" loading="lazy" />
                        </div>
                        <div className="col-lg-6">
                            <h1 className="display-5 fw-bold text-body-emphasis mb-3">Hello !!!</h1>
                            <p className="lead">You have successfully logged in!!</p>
                            <p className="lead">Explore and enjoy!!</p>
                            <div className="d-grid gap-3 d-md-flex justify-content-center me-5">
                                <Link className="border border-dark btn btn-primary btn-lg px-4 ms-5" to="/products">Products</Link>
                                <Link className="border border-dark btn btn-dark btn-lg px-4" to="/categories">Categories</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoggedInHomePage
