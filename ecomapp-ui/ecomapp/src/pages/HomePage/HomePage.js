import React, { useEffect } from 'react'
// import ProductPage from '../ProductsPage/ProductsPage1' // static data with props
// import ProductPage from '../ProductsPage/ProductsPage2' // static data with state
// import ProductPage from '../ProductsPage/ProductsPage3' // static data with state, events & hooks

import Banner from '../../components/Banner/Banner'
import LoggedInHomePage from './LoggedInHomePage'

const HomePage = () => {
  useEffect(() => {
    document.title = "Home";
  }, []);
  return (
    <main className="text-center mt-5 pt-2" >
      {/* <p>Welcome to React JS Training!!!</p> */}
      {!localStorage.getItem('token') && <Banner/>}
       {localStorage.getItem('token') && <LoggedInHomePage />}
    </main>
  )
}

export default HomePage
