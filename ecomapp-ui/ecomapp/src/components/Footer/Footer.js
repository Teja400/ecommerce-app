// rafce = React Arrow Function Export Component

import React from 'react'
import { NavLink } from 'react-router-dom'
import './Footer.css'

const Footer = () => {
  const year = 2024;
  const companyName = 'BlueYonder';

  return (
    // <footer className="text-center">
    //   <hr/>
    //   <p>&copy; Copy Right 2024 | BlueYonder</p>
    // </footer>
     <div className = "footer container-fluid mt-auto pt-3" style = {{backgroundColor: "#c9e2e5"}}>
        <footer className="">
        <ul className="nav justify-content-center footer-border pb-1 mb-1">
        <li className="nav-item"><NavLink to="/" className="nav-link pe-5 text-secondary">Home</NavLink></li>
        <li className="nav-item"><NavLink to="/contact" className="nav-link pe-5 text-secondary">Contact Us</NavLink></li>
        <li className="nav-item"><NavLink to="/faq" className="nav-link pe-5 text-secondary">FAQs</NavLink></li>
        <li className="nav-item"><NavLink to="/about" className="nav-link pe-5 text-secondary">About Us</NavLink></li>
        </ul> 
        <p className="text-center text-body-secondary">© {year} {companyName}</p>
       </footer>
     </div>
    
  )
}

export default Footer
