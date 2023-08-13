import React, { Component } from 'react';
import { ReactComponent as Bush } from "./icons/bush.svg";
import { ReactComponent as Tree } from "./icons/tree.svg";
import { ReactComponent as Pond } from "./icons/pond.svg";
import { ReactComponent as Rocks } from "./icons/rocks.svg";
import { ReactComponent as Grass } from "./icons/grass.svg";


class Icons extends Component {

    render() {
        const { iconKey } = this.props;

        const getIcon = 
            {
                tree: <Tree className='w-100 h-100' />,
                bush: <Bush className='w-100 h-100' />,
                pond: <Pond className='w-100 h-100' />,
                rocks: <Rocks className='w-100 h-100' />,
                grass: <Grass className='w-100 h-100' />,
            } 

        return (
            <>
                {getIcon[iconKey]}
            </>
        );
    };
}

export default Icons;
