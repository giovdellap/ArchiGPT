import React from "react";

const Footer = () => {
  return (
    <>
      <footer class="mb-0 text-center">
        <div class="d-flex align-items-center justify-content-center pb-5">
          <div class="col-md-6">
            <p class="mb-3 mb-md-0">Made by {" "}
              <a class="text-decoration-underline text-dark fs-5" href="/">CDC</a>
            </p>
            <a class="text-dark fs-4" href="/">
              <i class="fa fa-github"></i>
            </a>
          </div>
        </div>
      </footer>
    </>
  );
};

export default Footer