import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ReactRoundedImage from 'react-rounded-image';

import { findPlanterByUsername } from '../../services/planter-api';
import { findPlantById } from '../../services/plant-api';

import ReplyApp from '../reply/ReplyApp';
import DeletePost from './DeletePost';
import LikeButton from '../like-button.png';
import CurrentUser from "../contexts/CurrentUser";
import EditPost from "./EditPost";

function Post( {postId, username, plantId, gardenId, caption, photo, datetimePosted, likeCount, deletePostByPostId, editPostByPostId} ) {

    const defaultPlanter = {
        username: "",
        roleId: 2,
        firstName: "",
        lastName: "",
        email: ""
    }

    const defaultPlant = {
        plantId: 1,
        myGardenId: 1,
        plantDescription: "",
        photo: "",
        plantName: "",
        plantType: "",
        gotchaDate: ""
    }

    const defaultPost = {
        postId: postId,
        username: username,
        plantId: plantId,
        gardenId: gardenId,
        caption: caption,
        photo: photo,
        datetimePosted: datetimePosted,
        likeCount: likeCount
    }

    const [planter, setPlanter] = useState(defaultPlanter);
    const [plant, setPlant] = useState(defaultPlant);
    const [newPost, setNewPost] = useState(defaultPost);
    const [newCount, setNewCount] = useState(0);
    const auth = useContext(CurrentUser);

    useEffect(() => {
        findPlanterByUsername(username)
            .then((data) => setPlanter(data))
    }, [username]);

    useEffect(() => {
        findPlantById(plantId)
            .then((data) => setPlant(data))
    }, [plantId]);

    const increaseLikeCount = () => {
        setNewCount(newCount + 1);
    }

    const updatePost = () => {
        const updatedPost = {
            postId: postId,
            username: username,
            plantId: plantId,
            gardenId: gardenId,
            caption: caption,
            photo: photo,
            datetimePosted: datetimePosted,
            likeCount: newCount
        }

        const init = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${auth.token}`
            },
            body: JSON.stringify(updatedPost),
        };

        fetch(`http://localhost:8080/api/post/${postId}`, init)
            .then((response) => {
                if (response.status === 404) {
                    return Promise.reject("Post id not found");
                } else if (response.status !== 204) {
                    return Promise.reject("response is not 204 NO_CONTENT");
                }
            })
            .then(() => {
                editPostByPostId(newPost);
            }).then(setNewPost(updatedPost))
    }

    const handleClick = () => {
        increaseLikeCount();
        updatePost();
    }

    const postStyle = {
        "width": "1000px"
    }

    return(
        <div className="d-flex justify-content-center">
            <div className="card bg-light mt-3 mb-3" style={postStyle}>
                <div className="card-header">
                    <div className="d-flex flex-row-reverse">
                        <div>{datetimePosted}</div>
                    </div>
                </div>
                <div className="card-body">
                    <div className="row">
                        <div className="col">
                            <h4 className="card-title">
                            <Link to={`/my-garden/${planter.myGardenId}`} className="text-dark text-decoration-none">{planter.firstName} {planter.lastName}</Link>|
                            <Link to={`/plantprofile/${plant.plantId}`} className="text-dark text-decoration-none">{plant.plantName}</Link></h4>
                        </div>
                        <div className="col d-flex flex-row-reverse">
                            <EditPost postId={postId} editPostByPostId={editPostByPostId}/>
                            <DeletePost postId={postId} deletePostByPostId={deletePostByPostId}/>
                        </div>
                    </div>
                    <p className="card-text">{caption}</p>
                    <div className="d-flex justify-content-center">
                        <div style={{ display: "flex" }}>
                            <ReactRoundedImage
                                image={photo}
                                roundedColor=""
                                imageWidth="500"
                                imageHeight="350"
                                roundedSize="8"
                                borderRadius="30"
                            />
                        </div>
                    </div>
                    <div className="d-flex flex-row-reverse">
                        <button onClick={handleClick} className="btn btn-outline-light">
                            <img src={LikeButton} width="30px" alt="like"></img>
                            </button>
                        <div className="ml-3">
                            <p>{newPost.likeCount}</p>
                        </div>
                    </div>
                    <ReplyApp postId={postId}/>
                </div>
            </div>
        </div>
    );
}

export default Post;