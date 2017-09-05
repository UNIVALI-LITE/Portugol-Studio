/*
 *  YouTube HD Thumbnail
 *  jQuery plugin for creating high quality thumbnails for YouTube HD videos
 *  Version: 1.0
 *  Author: Simon Li
 *  http://www.simon-li.com
 *  MIT License
 *  
 *  
 *  This plugin was created based on jquery-boilerplate - v4.0.0
 *  http://jqueryboilerplate.com
 *
 *  Made by Zeno Rocha
 *  Under MIT License
 */

;( function( $, window, document, undefined ) {

	"use strict";

		var defaults = {
				darkenThumbnail: false
			};

		function YouTubeHDThumbnail ( element, options ) {
			this.elem = element;
			this.$elem = $(element);
			this.settings = $.extend( {}, defaults, options );
			this._defaults = defaults;
			this._name = 'youTubeHDThumbnail';
			this.init();
		}

		$.extend( YouTubeHDThumbnail.prototype, {
			init: function() {
				this.videoId = null,
				this.$thumbnail = null;

				// retrieve HD thumbnail
				var src = this.$elem.attr('src'),
					srcSplit = src.split('?'),
					srcMain = null,
					srcPure = null;

				if (srcSplit.length > 0){
					srcMain = srcSplit[0];
					srcPure = srcMain.split('/');
					this.videoId = srcPure.pop();
					this.$thumbnail = $('<a />')
						.attr({'href': '#'})
						.addClass('yt-hd-thumbnail')
						.append(
							$('<img/>').attr(
								{'src': 'http://i.ytimg.com/vi/'+ this.videoId + '/maxresdefault.jpg'}
							)
						);
				} else {
					console.log('The src attribute of iframe is not valid.');
					return;
				}

				// create container
				var $outerContainer = $('<div />')
					.addClass('yt-hd-thumbnail-outer-container')
					.insertAfter(this.elem)
					.css('width', this.$elem.attr('width')),

					$innerContainer = $('<div />')
						.addClass('yt-hd-thumbnail-inner-container')
						.appendTo($outerContainer);

				// insert thumbnail and iframe
				if (this.settings.darkenThumbnail){
					this.$thumbnail.addClass('yt-hd-thumbnail-darken');
				}
				$innerContainer.append(this.$thumbnail).append(this.elem);


				// add click handler to thumbnail
				var self = this;
				this.$thumbnail.on('click', function(e){
					e.preventDefault();
					src = src+'&autoplay=1';
					$innerContainer.addClass('yt-hd-thumbnail-clicked');
					self.$elem.attr({'src': src});
				});
			},
		} );

		$.fn[ 'youTubeHDThumbnail' ] = function( options ) {
			return this.each( function() {
				if ( !$.data( this, "plugin_" + 'youTubeHDThumbnail' ) ) {
					$.data( this, "plugin_" +
						'youTubeHDThumbnail', new YouTubeHDThumbnail( this, options ) );
				}
			} );
		};

} )( jQuery, window, document );