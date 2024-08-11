import React, { useEffect } from 'react'

const FaqPage = () => {
  useEffect(() => {
    document.title = "Faq";
  }, []);
  return (
    <div className='text-center mt-5 pt-5'>
      <h1>Faq Page</h1>
    </div>
  )
}

export default FaqPage
