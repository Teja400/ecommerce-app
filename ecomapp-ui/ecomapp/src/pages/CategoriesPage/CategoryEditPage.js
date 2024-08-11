import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link, useParams } from 'react-router-dom';
import './CategoriesPage.css';

const CategoryEdit = () => {
    if (localStorage.getItem('token') === null) {
        window.location.href = '/login';
    }
    let { id } = useParams();
    const [category, setCategory] = useState(0);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [isFailed, setIsFailed] = useState(false);
    const { register, handleSubmit, formState: { errors }, reset } = useForm();
    const onSubmit = (data) => {
        console.log(data);
        const category = {
            name: data.name,
            description: data.description
        }
        axios.put('http://localhost:8111/ecomapp/api/categories/' + id, category, {
            headers: {
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
          
          })
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
        axios.get('http://localhost:8111/ecomapp/api/categories/' + id, {
            headers: {
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
          
          })
            .then((res) => {
                setCategory(res.data);
            })
            .catch(err => console.log(err))
            .finally(() => console.log('Finally'));
    }, [id]);

    useEffect(() => {
        reset(category);
    }, [reset, category]);

    useEffect(() => {
        if (isFailed) {
            const timer = setTimeout(() => {
                setIsFailed(false);
            }, 5000);

            return () => clearTimeout(timer);
        }
    }, [isFailed]);

    useEffect(() => 
    {
        document.title = "Categories/Edit Category";
    })


    return (
        <div className='categories-page'>
            <h3 className=" text-center mt-4 pt-5">Edit {category.name}</h3>
            <div className="d-flex justify-content-center algin-items-center my-4">
                <form className="shadow-lg category-edit-form col-md-6 w-50 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">
                            Category Name
                        </label>
                        <input  {...register('name', { required: true })} type="text" className={`border border-dark form-control ${errors.name && 'is-invalid'}`} id="name" name="name" onChange={handleChange} placeholder='Name of the category' />
                        {errors.name && <span className='text-danger'>Category name is required</span>}
                    </div>
                    <div className="mb-3">
                        <label htmlFor="description" className="form-label">
                            Description
                        </label>
                        <textarea {...register("description", { required: true })} type="text" className={`border border-dark form-control ${errors.description && 'is-invalid'}`} id="description" name="description" onChange={handleChange} placeholder='Description of the category' />
                        {errors.description && <span className='text-danger'>Description is required</span>}
                    </div>

                    {isSubmitted && <div className="alert alert-success">Saved Successfully!</div>}
                    {(errors.name || errors.description) && (
                        <div className="alert alert-danger">Fill all the details and try again!</div>
                    )}
                    {(isFailed) && (
                        <div className="alert alert-danger">Check the details and try again!</div>
                    )}
                    <button type="submit" className="btn btn-responsive btn-success border border-dark">
                        Submit
                    </button>

                    <Link to="/categories" className='btn btn-responsive btn-primary m-3 border border-dark'>Back</Link>

                </form>
            </div>
        </div>
    )
}


export default CategoryEdit