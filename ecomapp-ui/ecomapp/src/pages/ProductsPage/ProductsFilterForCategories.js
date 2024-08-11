import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import { useCategories } from '../../contexts/CategoryContext';
import './ProductsPage.css';

const ProductsFilter = () => {
    if (localStorage.getItem('token') === null) {
        window.location.href = '/login';
    }
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [categories, loading] = useCategories([]);
    const [products, setProducts] = useState([]);
    const [hasProducts, setHasProducts] = useState(true);


    const onSubmit = result => {
        console.log(result);
        axios.get('http://localhost:8111/ecomapp/api/products/product-with-category?categoryId=' + result.categoryId, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then((res) => {
                console.log(res);
                setProducts(res.data);
                console.log(res.data)
                if (res.data.length === 0) {
                    setHasProducts(false);
                }
            })
            .catch((err) => {
                console.log("Hello " + err);
            })
            .finally(() => {
                console.log('It is over');
            })
    }

    useEffect(() => {
        document.title = "Products/Search Products by Category";
    })

    return (
        <div className='products-page'>
            <h3 className="text-center mt-4 pt-5">Search Products by Category</h3>

            <form className="d-flex container-fluid col-md-6 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                <select {...register("categoryId")} type="text" className={`me-1 ms-5 w-50 border border-dark form-control ${errors.categoryId && 'is-invalid'}`} id="categoryId" name="categoryId" placeholder='Select Category'>
                    {console.log(errors.categoryId)}
                    {categories.map((category) => {
                        return (
                            <option value={category.id} key={category.id}>{category.name}</option>
                        )
                    })}
                </select>
                <button type="submit" className="btn btn-responsive btn-success border border-dark">
                    Search
                </button>
            </form>

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

export default ProductsFilter
