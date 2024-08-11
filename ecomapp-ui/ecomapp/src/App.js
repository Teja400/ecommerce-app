import './App.css';
import Header from './components/Header/Header';
import HomePage from './pages/HomePage/HomePage';
import Footer from './components/Footer/Footer';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import ProductsPage from './pages/ProductsPage/ProductsPage';
import CategoriesPage from './pages/CategoriesPage/CategoriesPage';
import AboutPage from './pages/AboutPage/AboutPage';
import LoginPage from './pages/LoginPage/LoginPage';
import RegisterPage from './pages/RegisterPage/RegisterPage';
import FaqPage from './pages/FaqPage/FaqPage';
import ContactUsPage from './pages/ContactUsPage/ContactUsPage';
import ProductsAddPage from './pages/ProductsPage/ProductsAddPage';
import ProductDetail from './pages/ProductsPage/ProductsDetailPage';
import ProductsEditPage from './pages/ProductsPage/ProductsEditPage';
import ProductToCategoryLink from './pages/ProductsPage/ProductToCategoryLink';
import { CategoryProvider } from './contexts/CategoryContext';
import CategoryAddPage from './pages/CategoriesPage/CategoryAddPage';
import CategoryEditPage from './pages/CategoriesPage/CategoryEditPage';
import CategoryDetailPage from './pages/CategoriesPage/CategoryDetailPage';
import { CartProvider } from './contexts/CartContext';
import ProductCart from './pages/ProductsPage/ProductCart';
import ProductsSearch from './pages/ProductsPage/ProductsSearch';
import ProductsFilterForCategories from './pages/ProductsPage/ProductsFilterForCategories';
import CategorySearch from './pages/CategoriesPage/CategorySearch';
// import AuthProvider from "./contexts/AuthContext";
// import RoutesAuth from "../src/Auth/Routes";

function App() {
  return (
    <div className="d-flex flex-column min-vh-100">
      <BrowserRouter>
      <CartProvider>
        <Header />
        <CategoryProvider>
            <Routes>
              <Route path='/' element={<HomePage />} />
              <Route path='/about' element={<AboutPage />} />
              <Route path='/login' element={<LoginPage />} />
              <Route path='/registration' element={<RegisterPage />} />
              <Route path='/contact' element={<ContactUsPage />} />
              <Route path="/faq" element={<FaqPage />} />

              {/* Product Routes */}
              <Route path='/products' element={<ProductsPage />} />
              <Route path="/products/add" element={<ProductsAddPage />} />
              <Route path='/products/:id' element={<ProductDetail />} />
              <Route path='/products/edit/:id' element={<ProductsEditPage />} />
              <Route path='/products/linktocategory/:id' element={<ProductToCategoryLink />} />
              <Route path = '/products/cart' element = {<ProductCart/>} />
              <Route path = '/products/search' element = {<ProductsSearch/>} />
              <Route path = '/products/filter' element = {<ProductsFilterForCategories/>} />

              {/* Category Routes */}

              <Route path='/categories' element={<CategoriesPage />} />
              <Route path='/categories/add' element={<CategoryAddPage />} />
              <Route path='/categories/edit/:id' element={<CategoryEditPage />} />
              <Route path='/categories/:id' element={<CategoryDetailPage />} />
              <Route path = '/categories/search' element = {<CategorySearch />} />
            </Routes>
        </CategoryProvider>
        <Footer />
        </CartProvider>
      </BrowserRouter>
    </div>
  );
}

export default App;
