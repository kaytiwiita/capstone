import Login from "./Login";
import Logo from "./images/plantbase-logo2.png";
import WelcomeImage from "./images/welcome-image (2).png";

function Welcome() {
  return (
    <div
      className="bg-image"
      style={{
        backgroundImage:
          "url(https://media.istockphoto.com/vectors/horizontal-vector-illustration-of-an-empty-light-smoky-blue-gray-vector-id1177688756?b=1&k=6&m=1177688756&s=170667a&w=0&h=t3dpwnpMAT4jWgrrRbd47Umv4y-XI7mVUPtKzux5p04=)",
        height: "100rem",
        backgroundAttachment: "fixed",
      }}
    >
    <div className="large-screen" id='welcome-page'>
      <div className="container-fluid" style={{flexWrap: 'wrap'}}>
        <div className="col" id='welcome-page'>
            <div
              className="card text-white mt-3 mb-3 text-center"
              style={{
                backgroundColor: "rgba(255, 255, 255, 0.5)",
                backdropFilter: "blur(3px)",
                paddingTop: "20px",
              }}
            >
              <h1
                className="text-center"
                style={{
                  color: "rgba(89, 107, 93, 1)",
                  fontFamily: "Century Gothic",
                  textShadow:
                    "-1px 1px 0 #000, 1px 1px 0 #000, 1px -1px 0 #000, -1px -1px 0 #000",
                }}
              >
                <img
                  src={Logo}
                  alt="logo"
                  width="50px"
                  style={{
                    position: "relative",
                    bottom: "8px",
                    textShadow: "none",
                  }}
                />
                lantbase{" "}
              </h1>
              <p
                className="card-body"
                style={{
                  color: "rgba(133, 166, 141, 1)",
                  fontFamily: "Century Gothic",
                }}
              >
                A social media platform specifically for the plant community
              </p>
              <div className="text-center">
                <img
                  src={WelcomeImage}
                  alt="welcome"
                  style={{
                    justifyContent: "center",
                    alignItems: "center",
                    marginBottom: "50px",
                    width: "100%",
                    height: "12em",
                    objectFit: "contain",
                  }}
                />
              </div>
            </div>
        </div>
          <div className="col" id="welcome-page">
            <Login />
          </div>
          </div>
      </div>
    </div>
  );
}

export default Welcome;
