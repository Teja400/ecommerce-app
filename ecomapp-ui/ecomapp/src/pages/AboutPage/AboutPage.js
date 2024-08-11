import React, { useEffect } from 'react'

const AboutPage = () => {
  useEffect(() => 
  {
    document.title = "About";
  })
  return (
    <div className = "text-center mt-5 pt-5">
        <h1>About Page</h1>
    </div>
  )
}

export default AboutPage
 