import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link } from 'react-router-dom';
import './CategoriesPage.css';

const CategorySearch = () => {
    if (localStorage.getItem('token') === null) {
        window.location.href = '/login';
    }
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [categories, setCategories] = useState([]);
    const [hasCategories, setHasCategories] = useState(true);
    const handleChange = (e) => {
        console.log(e.target.value);
    }
    const onSubmit = data => {
        console.log(data);
        let mapping = {};
        if (data.searchMethod === 'findByName') {
            mapping = {
                name: data.searchItem
            }
        }
        else if (data.searchMethod === 'findByDescription') {
            mapping = {
                description: data.searchItem
            }
        }
        console.log(mapping);
        axios.post('http://localhost:8111/ecomapp/api/categories', mapping, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            .then((res) => {
                console.log(res);
                setCategories(res.data);
                if(res.data.length === 0) {
                    setHasCategories(false);
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
        document.title = "Categories/Search Data by Fields";
    })

    return (
        <div className='categories-page'>
            <h3 className="text-center mt-4 pt-5">Categories Search By Fields</h3>
            <form className="d-flex container-fluid  col-md-6 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                <select {...register("searchMethod")} type="text" className={`me-1 w-50 border border-dark form-control ${errors.categoryId && 'is-invalid'}`} id="searchMethod" name="searchMethod" >
                    {console.log(errors.categoryId)}
                    <option value="findByName">find By Name</option>
                    <option value="findByDescription">find By Description</option>
                </select>
                <input  {...register('searchItem')} type="text" className={`me-3 border border-dark form-control ${errors.name && 'is-invalid'}`} id="searchItem" name="searchItem" onChange={handleChange} placeholder='Search' />
                <button type="submit" className="btn btn-responsive btn-success border border-dark">
                    Search
                </button>
            </form>

            <div className='row p-4 m-0'>
                {hasCategories ? (
                    categories.map((category) => {
                        return (

                            <div className='col-md-4 my-2' key={category.id}>
                                <div className="card shadow-lg category-card mb-2">
                                    <img src="https://t4.ftcdn.net/jpg/05/36/24/13/360_F_536241340_GsrsNhcWC0hyTVaJLilNafyDw6fl0cC8.jpg" className="card-img-top" alt="Card" />
                                    <div className="card-body category-card-body">
                                        <h5 className="text-center card-title mb-3">{category.name}</h5>
                                        <p className="card-text mb-2 text-body-secondary text-truncate"><strong>Description:</strong> {category.description}</p>
                                        <div className='d-flex align-items-center justify-content-center'>
                                            <Link to={'/categories/' + category.id} className="card-link fw-semibold category-link">View</Link>
                                        </div>
                                
                                    </div>
                                </div>
                            </div>
                        )
                    })) : <div className='alert alert-warning'>No categories found</div>}
            </div>
        </div>
    )

}

export default CategorySearch
