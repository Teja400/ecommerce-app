import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link, useParams } from 'react-router-dom';
import './ProductsPage.css';
import { useCategories } from '../../contexts/CategoryContext';

const ProductEdit = () => {
    if (localStorage.getItem('token') === null) {
        window.location.href = '/login';
    }
    let { id } = useParams();
    const [product, setProduct] = useState(0);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [isFailed, setIsFailed] = useState(false);
    const { register, handleSubmit, formState: { errors }, reset } = useForm();
    const [categories] = useCategories([]);
    const onSubmit = (data) => {
        console.log(data);
        const product = {
            name: data.name,
            price: data.price,
            description: data.description
        }
        axios.put('http://localhost:8111/ecomapp/api/products/' + id, product, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
        }})
            .then((res) => {
                console.log(res);
                setIsSubmitted(true);
            })
            .catch((err) => {
                console.log(err);
                setIsFailed(true);
            })
            .finally(() => {
                console.log('It is over');
            })

    };
    const handleChange = (e) => {
        console.log(e.target.value);
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
        document.title = "Products/Edit Product";
        reset(product);
    }, [reset, product]);

    useEffect(() => {
        if (isFailed) {
            const timer = setTimeout(() => {
                setIsFailed(false);
            }, 5000);

            return () => clearTimeout(timer);
        }
    }, [isFailed]);



    return (
        <div className = 'products-page'>
            <h3 className=" text-center mt-4 pt-5">Edit {product.name}</h3>
            <div className="d-flex justify-content-center algin-items-center my-4">
                <form className="product-edit-form col-md-6 w-50 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">
                            Product Name
                        </label>
                        <input  {...register('name', { required: true })} type="text" className={`border border-dark form-control ${errors.name && 'is-invalid'}`} id="name" name="name" onChange={handleChange} placeholder='Name of the product' />
                        {errors.name && <span className='text-danger'>Product name is required</span>}
                    </div>
                    <label htmlFor="price" className="form-label">
                        Price
                    </label>
                    <div className="input-group mb-3">
                        <span class="input-group-text">₹</span>
                        <input {...register("price", { required: true })} type='number' step='0.001' className={`border border-dark form-control ${errors.price && 'is-invalid'}`} id="price" name="price" onChange={handleChange} placeholder='Price of the product' />
                        {errors.price && <span className='text-danger'>Price is required</span>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">
                            Description
                        </label>
                        <textarea {...register("description", { required: true })} type="text" className={`border border-dark form-control ${errors.description && 'is-invalid'}`} id="description" name="description" onChange={handleChange} placeholder='Description of the product' />
                        {errors.description && <span className='text-danger'>Description is required</span>}
                    </div>

                   {product.categoryId > 0 && ( <div className="mb-3">
                        <label htmlFor="categoryId" className="form-label">
                            Choose a Category
                        </label>
                        <select {...register("categoryId", { required: true })} type="text" className={`border border-dark form-control ${errors.categoryId && 'is-invalid'}`} id="categoryId" name="categoryId" onChange={handleChange} placeholder='Category of the product' >
                            {categories.map((category) => (
                                <option value={category.id} key={category.id}>
                                    {category.name}
                                </option>
                            ))}
                        </select>
                        {errors.categoryId && <span className='text-danger'>Category Id is required</span>}
                    </div>)}



                    {isSubmitted && <div className="alert alert-success">Saved Successfully!</div>}
                    {(errors.name || errors.description || errors.price || errors.categoryId) && (
                        <div className="alert alert-danger">Fill all the details and try again!</div>
                    )}
                    {(isFailed) && (
                        <div className="alert alert-danger">Check the details and try again!</div>
                    )}
                    <button type="submit" className="btn btn-responsive btn-success border border-dark">
                        Submit
                    </button>

                    <Link to="/products" className='btn btn-responsive btn-primary m-3 border border-dark'>Back</Link>

                </form>
            </div>
        </div>
    )
}


export default ProductEdit