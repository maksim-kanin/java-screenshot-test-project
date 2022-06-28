<div class="carousel-item active" data-mdb-interval="1000000000">
    <figure class="figure">
        <div class="controls">
            <span class="badge rounded-pill badge-light border border-danger">
              <i class="fas fa-camera-retro" style="color: red"></i> Actual
            </span>
        </div>
        <img id="${id}-actual" src="data:image/png;base64,${actual}"
             class="img-fluid"
             alt="Actual"/>
        <div class="controls">
            <button id="${id}-update" class="button rounded-pill border border-light"
                    data-mdb-toggle="modal" data-mdb-target="#${id}-modal">
                <i class="fas fa-save" style="color: royalblue"></i> Update
            </button>
            <button class="button rounded-pill border border-light" onclick="scaleActual()">
                <i class="fas fa-search" style="color: royalblue"></i>
                Zoom
            </button>
            <button class="button rounded-pill border border-light" onclick="showActualIgnoring()">
                <i class="fas fa-adjust" style="color: royalblue"></i>
                Show ignoring
            </button>
        </div>
    </figure>
</div>