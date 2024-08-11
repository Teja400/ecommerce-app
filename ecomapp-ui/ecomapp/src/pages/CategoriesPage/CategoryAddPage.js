import axios from 'axios';
import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import './CategoriesPage.css';

const CategoryAddPage = () => {
    if(localStorage.getItem('token') === null)
    {
        window.location.href = '/login';
    }
    // const navigate = useNavigate();
    const [isSubmitted, setIsSubmitted] = React.useState(false);
    const [isFailed, setIsFailed] = React.useState(false);
    const { register, handleSubmit, formState: { errors } } = useForm();
    const handleChange = (e) => {
        console.log(e.target.value);
    }
    const onSubmit = data => {
        console.log(data);
        // alert(data.name + " " + data.price + " " + data.description);
        const category = {
            name: data.name,
            description: data.description
        }
        axios.post('http://localhost:8111/ecomapp/api/categories/add-category', category, {
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
        // if(submitCount === 0)
        // {
        //     navigate('/categories');
        // }
    }

    useEffect(() => {
        if (isFailed) {
            const timer = setTimeout(() => {
                setIsFailed(false);
            }, 1000);

            return () => clearTimeout(timer);
        }
    }, [isFailed]);

    useEffect(() => {
        document.title = 'Categories/Add Category';
    }, []);


    return (
        <div className = 'categories-page mt-2'>
            <h3 className="text-center mt-5 pt-5 me-0">Add a category</h3>
            <div className="d-flex justify-content-center algin-items-center my-4 me-0">
                <form className="shadow-lg category-add-form col-md-6 w-50 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
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
                        {errors.description && <span className='text-danger'>Category is required</span>}
                    </div>



                    {isSubmitted && <div className="alert alert-success">Saved Successfully!</div>}
                    {(errors.name || errors.password) && (
                        <div className="alert alert-danger">Fill all the details and try again!</div>
                    )}
                    {(isFailed) && (
                        <div className="alert alert-danger">Enter valid details and try again!</div>
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

export default CategoryAddPage
