import { Link } from 'react-router-dom';
import React, {useContext} from 'react';

function Nav() {
    // const auth = useContext(AuthContext);

    const navStyle = {
        color: 'white',
        'text-decoration': 'none'
    };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container-fluid">
            <Link style={navStyle} to="/" ><strong>🌱</strong>
            </Link>
            <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor03" aria-controls="navbarColor03" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarColor03">
            <ul  className=" navbar-nav me-auto nav-links">
                <Link style={navStyle} to="/garden" className="nav-link">
                    <li style={navStyle} >Garden</li>
                </Link>
                <Link style={navStyle} to="/my-garden" className="nav-link">
                    <li style={navStyle} >My Garden</li>
                </Link>
                {/* <Link style={navStyle} className="nav-link btn-secondary" to="/profile">
                    <li className="nav-item">
                        <strong>
                            user: {auth.currentUser && auth.currentUser.isValid() ? (
                                        auth.currentUser.username
                                    ) : (
                                        ""
                                    )}
                        </strong>
                    </li>
                </Link>
                <Link style={navStyle} className="nav-link" to="/logout">
                    <li className="nav-item">
                        <Logout />
                    </li>
                </Link> */}
            </ul>
            <form className="d-flex">
                <input className="form-control me-sm-2" type="text" placeholder="Search"/>
                <button className="btn btn-light my-2 my-sm-0" type="submit"><strong>Search</strong></button>
            </form>
        </div>
        </div>
    </nav>
  );
}

export default Nav;