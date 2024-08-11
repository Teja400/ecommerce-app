import React, { useEffect } from 'react'

const ContactUsPage = () => {
  useEffect(() => {
    document.title = "Contact Us";
  }, []);
  return (
  
    <div className='text-center mt-5 pt-5'>
      <h1>Contact Us Page</h1>
    </div>
  )
}

export default ContactUsPage
