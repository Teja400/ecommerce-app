import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link, useParams } from 'react-router-dom';
import { useCategories } from '../../contexts/CategoryContext';
import './ProductsPage.css';

const ProductToCategoryLink = () => {
    if (localStorage.getItem('token') === null) {
        window.location.href = '/login';
    }
    const { id } = useParams();
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [isFailed, setIsFailed] = useState(false);
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [categories] = useCategories([]);
    const [product, setProduct] = useState({});
    const handleChange = (e) => {
        console.log(e.target.value);
    }
    const onSubmit = data => {
        console.log(data);
        // console.log(typeof data.categoryId);
        const productWithCategory = {
            productId: product.id,
            categoryId: data.categoryId
        }
        axios.post('http://localhost:8111/ecomapp/api/products/product-with-category', productWithCategory, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then((res) => {
                console.log(res);
                setIsSubmitted(true);
            })
            .catch((err) => {
                console.log("Hello " + err);
                setIsFailed(true);
            })
            .finally(() => {
                console.log('It is over');
            })
    }
    useEffect(() => {
        axios.get('http://localhost:8111/ecomapp/api/products/' + id, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
        }})
            .then((res) => {
                setProduct(res.data);
            })
            .catch(err => console.log(err))
            .finally(() => console.log('Finally'));
    }, [id]);

    useEffect(() => {
        if (isFailed) {
            const timer = setTimeout(() => {
                setIsFailed(false);
            }, 5000);

            return () => clearTimeout(timer);
        }
    }, [isFailed]);

    useEffect(() => {
        document.title = "Products/Link Product to Category";
    })

    return (
        <div className='products-page'>
            <h3 className="text-center mt-4 pt-5">Link {product.name} to a category</h3>
            <div className="d-flex justify-content-center algin-items-center my-4">
                <form className="product-link-form col-md-6 w-50 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-3">
                        <label htmlFor="productId" className="form-label">
                            Product Name
                        </label>
                        <input  {...register('productId',)} type="text" className={`border border-dark form-control ${errors.name && 'is-invalid'}`} id="productId" name="productId" onChange={handleChange} placeholder='Id of the product' value={product.name} />
                        {errors.productId && <span className='text-danger'>Product Name is required</span>}
                    </div>
                    {/* <div className="mb-3">
                        <label htmlFor="categoryId" className="form-label">
                            Category Id
                        </label>
                        <input  {...register('categoryId', { required: true })} type="number" className={`border border-dark form-control ${errors.name && 'is-invalid'}`} id="categoryId" name="categoryId" onChange={handleChange} placeholder='Id of the category' />
                        {errors.categoryId && <span className='text-danger'>Product Id is required</span>}
                    </div> */}
                    <div className="mb-3">
                        <label htmlFor="categoryId" className="form-label">
                            Choose a Category
                        </label>
                        <select {...register("categoryId", { required: true })} type="number" className={`border border-dark form-control ${errors.categoryId && 'is-invalid'}`} id="categoryId" name="categoryId" onChange={handleChange} placeholder='Category of the product' >
                            {categories.map((category) => (
                                <option value={category.id} key={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                        {errors.categoryId && <span className='text-danger'>Category Id is required</span>}
                    </div>

                    {isSubmitted && <div className="alert alert-success">Saved Successfully!</div>}
                    {(errors.productId || errors.categoryId) && (
                        <div className="alert alert-danger">Fill all the details and try again!</div>
                    )}
                    {(isFailed) && (
                        <div className="alert alert-danger">Enter valid details and try again!</div>
                    )}
                    <button type="submit" className="btn btn-responsive btn-success border border-dark">
                        Submit
                    </button>

                    <Link to="/products" className='btn btn-responsive btn-primary border border-dark m-3'>Back</Link>

                </form>
            </div>
        </div>
    )
}

export default ProductToCategoryLink
