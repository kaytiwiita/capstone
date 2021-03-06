import { Link } from 'react-router-dom';
// import EditPlant from './EditPlant';

function Plant({plants = [], plant, editPlant, myGardenId, editPlantByPlantId}) {
    const plantStyle = {
        'width': '18rem',
        'marginBottom': '10px',
        'marginLeft': '30px',
        'marginTop': '30px',
        'textDecoration': 'none'
    };

    const navStyle = {
        color: 'green',
        'textDecoration': 'none'
    };

    return (
    <div className="" style={plantStyle}>
        <Link to={`/plantprofile/${plant.plantId}`} style={navStyle}>
            <div className="card bg-light mt-3" style={{'height': '20rem'}} key={plant.plantId} >
                <div  className="col" style={{ marginTop: '30px'}}>
                <span className="badge" style={{backgroundColor: 'rgba(133, 166, 141, 1)'}}>{plant.plantId}</span>
                </div>
                <div className="col" style={{fontFamily: 'Century Gothic', color: 'rgba(89, 107, 93, 1)'}}><strong>{plant.plantName}</strong></div>
                <div className="col" style={{fontFamily: 'Century Gothic', color: 'rgba(89, 107, 93, 1)'}}>{plant.plantType}</div>
                <div className="col"><img src={plant.photo} style={{ alignSelf: 'center', marginBottom: '10px', marginTop: '10px', width: '100%', height: '11rem', objectFit: 'contain'}} alt="plant list item"></img></div>
                <div className="col"></div>
            </div>
        </Link>
    </div>
    );
}

export default Plant;