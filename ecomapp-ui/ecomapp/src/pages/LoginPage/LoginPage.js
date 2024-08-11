import React, { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form';
import { Link, useNavigate} from 'react-router-dom';
import './LoginPage.css';
import axios from 'axios';

const LoginPage = () => {
    const navigate = useNavigate();
    const {register, handleSubmit, formState: { errors}} = useForm();
    const [showPassword, setShowPassword] = useState(false);
    const [isFailed, setIsFailed] = React.useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const handleChange = (e) => {
        console.log(e.target.value);
    }
    const onSubmit = data => 
    {
        console.log(data);
        // alert(data.name + " " + data.password);
        const user = 
        {
            username: data.username,
            password: data.password
        }
        axios.post('http://localhost:7070/ecomapp-auth/auth/login', user)
            .then((res) => {    
                console.log(res);
                console.log(res.data.token);
                localStorage.setItem('token', res.data.token);
                setIsSubmitted(true);
                //redirect user to home page
                window.location.href = '/'
                
            })
            .catch((err) => {
                console.log(err);
                setIsFailed(true);
            })
            .finally(() => {
                console.log('It is over');
            })
            if(isSubmitted)
            {  
                navigate('/');
            }
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
        <div className="login-page d-flex justify-content-center algin-items-center mt-5 pt-5 pb-4">
            <form className="login-form col-md-6 w-25 border border-success rounded p-4" onSubmit={handleSubmit(onSubmit)}>
                <h5 className="login-text text-center pb-3">Login</h5>
                <div className = "d-flex ps-5">
                    <p className = "ps-4 ms-4"> New User ? </p>
                    {" "}
                    <Link to="/registration" className="">Register</Link>
                </div>
                <div className="mb-3">
                    <label htmlFor="userName" className="form-label">
                        Username
                    </label>
                    <input  {...register('username', { required: true })} type="text" className={`border border-dark form-control ${errors.username && 'is-invalid'}`} id="userName" name="username" onChange = {handleChange}/>
                    {errors.username && <span className='text-danger'>Username is required</span>}
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">
                        Password
                    </label>
                    <input {...register("password", { required: true })} type={showPassword ? 'text' : 'password'} className={`border border-dark form-control ${errors.password && 'is-invalid'}`} id="password" name="password" onChange={handleChange}/>
                    {errors.password && <span className='text-danger'>Password is required</span>}
                </div>
                <div>
                        <input type="checkbox" className="border border-dark form-check-input me-2 border" id="exampleCheck1" onClick={() => setShowPassword(!showPassword)}/> 
                        <label className="form-check-label" htmlFor="exampleCheck1">Show Password</label>
                </div>
            
                

                {/* {isSubmitted && <div className="alert alert-success">Saved Successfully!</div>} */}
                {(errors.username || errors.password) && (
                    <div className="alert alert-danger">Fill all the details and try again later!</div>
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

export default LoginPage
