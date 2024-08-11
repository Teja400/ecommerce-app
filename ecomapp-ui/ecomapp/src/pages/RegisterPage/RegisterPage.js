import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link} from 'react-router-dom';
import './RegisterPage.css';
import axios from 'axios';

const RegisterPage = () => {
    const { register, handleSubmit, formState: { errors}} = useForm();
    const [showPassword, setShowPassword] = useState(false);
    const [isFailed, setIsFailed] = React.useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const handleChange = (e) => {
        console.log(e.target.value);
    }
    const onSubmit = data => 
    {
        console.log(data);
        const user =
        {
            username: data.username,
            email: data.email,
            password: data.password,
            role: 'admin',
            active: true
        }
        axios.post('http://localhost:7070/ecomapp-auth/registration', user)
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
        
    }

    useEffect(() => {
        if (isFailed) {
            const timer = setTimeout(() => {
                setIsFailed(false);
            }, 5000);

            return () => clearTimeout(timer);
        }
    }, [isFailed]);

    return (
        <div className="register-page d-flex justify-content-center algin-items-center mt-5 pb-5 pt-5 ">
            <form className="register-form col-md-6 w-25 rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                <h5 className="register-text text-center pb-3">Register</h5>
                <div className = "d-flex ps-5">
                    <p className = "ps-4"> Registered user ? </p>
                    {" "}
                    <Link to="/login" className="">Login</Link>
                </div>
                <div className="mb-3">
                    <label htmlFor="userName" className="form-label">
                        Username
                    </label>
                    <input  {...register("username", { required: true })} type="text" className={`border border-dark form-control ${errors.username && 'is-invalid'}`} id="userName" name="username" onChange = {handleChange}/>
                    {errors.username && <span className='text-danger'>Username is required</span>}
                </div>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">
                        Email Address
                    </label>
                    <input {...register("email", { required: true })} type="text" className={`border border-dark form-control ${errors.email && 'is-invalid'}`} id="email" name="email"/>
                    {errors.password && <span className='text-danger'>Email is required</span>}
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Password
                    </label>
                    <input {...register("password", { required: true })} type={showPassword ? 'text' : 'password'} className={`border border-dark form-control ${errors.password && 'is-invalid'}`} id="password" name="password"/>
                    {errors.password && <span className='text-danger'>Password is required</span>}
                </div>
                
                <div>
                        <input type="checkbox" className="border border-dark form-check-input me-2 border" id="exampleCheck1" onClick={() => setShowPassword(!showPassword)}/> 
                        <label className="form-check-label" htmlFor="exampleCheck1">Show Password</label>
                </div>
            
                

                {isSubmitted && <div className="alert alert-success mt-2">Saved Successfully!</div>}
                {(errors.username || errors.password || errors.email) && (
                    <div className="alert alert-danger mt-2">Fill all the details and try again later!</div>
                )}
                {(isFailed) && (
                        <div className="alert alert-danger">Enter valid details and try again!</div>
                    )}
                <button type="submit" className="btn btn-success">
                    Submit
                </button>

                <Link to="/" className='btn btn-primary m-3'>Back</Link>

            </form>
        </div>
    )
}

export default RegisterPage
