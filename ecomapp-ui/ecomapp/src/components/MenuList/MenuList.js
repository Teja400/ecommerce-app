import React from 'react'
import { NavLink } from 'react-router-dom';
import '../Header/Header.css';
const MenuList = (props) => {

  return (
    <>
      <style>
        {`
        .active-link{
          color: red;
          background-color: #0d6efd;
          border-radius: 5px;
        }
        `}
      </style>
      <div className="d-flex justify-content-center align-items-center flex-md-wrap">
        <li className="nav-item"><NavLink to="/"  className="nav-link pe-5"
          style={({ isActive }) =>
          isActive
            ? {
                color: '#12372A',
                fontWeight: '620',
              }
            : { color: '#12372A' }
        }
        >{props.menuItem1}</NavLink></li>
        <li className="nav-item"><NavLink to="/products"  className="nav-link pe-5"
         style={({ isActive }) =>
         isActive
           ? {
               color: '#12372A',
               fontWeight: '620',
             }
           : { color: '#12372A' }
         }
        >{props.menuItem2}</NavLink></li>
        <li className="nav-item"><NavLink to="/categories"  className="nav-link pe-5"
          style={({ isActive }) =>
          isActive
            ? {
                color: '#12372A',
                fontWeight: '620',
              }
            : { color: '#12372A' }
          }
        >{props.menuItem3}</NavLink></li>
      </div>
    </>

  )
}

export default MenuList
