// import
import Menulist from '../MenuList/MenuList';
import './Header.css';
import { Link } from 'react-router-dom';
import { useCart } from '../../contexts/CartContext';

// Component Definition
const Header = () => {
  const { cart } = useCart();
  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/';
  }

  return (
    <nav className="navbar navbar-expand-md navbar-light fixed-top p-3 mb-2">
      <div className="container-fluid">
        <Link className="navbar-brand fw-bold" to='/'>EcomApp</Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className=" collapse navbar-collapse" id="navbarCollapse">
          <ul className="navbar-nav mx-auto mb-2 mb-md-0 ">
            <Menulist menuItem1="Home" menuItem2="Products" menuItem3="Categories" />
          </ul>
        </div>
      </div>
      {localStorage.getItem('token') && <Link type="button" className="cart-btn btn-responsive btn btn-dark border border-dark me-2" to = '/products/cart'>
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="currentColor" class="bi bi-cart" viewBox="0 0 16 16">
          <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"></path>
        </svg>
        {cart.length}
      </Link>}
    
      {localStorage.getItem('token') && <button className='btn-responsive btn btn-danger border border-dark' onClick={handleLogout}>Logout</button>}

    </nav>
  )
}

// export
export default Header